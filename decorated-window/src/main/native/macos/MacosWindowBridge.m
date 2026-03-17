// MacosWindowBridge.m — JNI bridge for macOS window decoration.
// Transparent title bar, traffic-light repositioning, invisible toolbar,
// and fullscreen replacement buttons.

#import <Cocoa/Cocoa.h>
#import <QuartzCore/QuartzCore.h>
#import <jni.h>
#import <math.h>
#import <objc/runtime.h>
#import <stdatomic.h>

// ─── Constants ──────────────────────────────────────────────────────────────────

static const float kMinHeightForFullSize  = 28.0f;
static const float kDefaultButtonOffset   = 23.0f;
static const float kToolbarExtraInset     = 6.0f;
static const float kDefaultTitleBarHeight = 40.0f;
static const float kMaxButtonLeftMargin   = kDefaultTitleBarHeight / 2.0f;

// Associated-object keys
static const char kTitleBarConstraintsKey;
static const char kTitleBarHeightKey;
static const char kFullscreenObserverKey;
static const char kFullscreenButtonsKey;
static const char kDragViewKey;
static const char kZoomResponderKey;
static const char kResizeObserverKey;

static atomic_bool sShutdownInProgress = false;

// Forward declarations
static void removeExistingConstraints(NSWindow *window);
static void removeDragView(NSWindow *window);
static void applyConstraints(NSWindow *window, float height);
static void ensureDragView(NSWindow *window);
static void installFullScreenButtons(NSWindow *window, float titleBarHeight);
static void removeFullScreenButtons(NSWindow *window);
static void updateFullScreenButtonsPosition(NSWindow *window);

// ─── MacosUIDragView ────────────────────────────────────────────────────────────
// Transparent view that forwards events to contentView, enabling window
// drag when movable=NO.

@interface MacosUIDragView : NSView
@property (nonatomic, strong) NSEvent *lastMouseDownEvent;
@end

@implementation MacosUIDragView

- (NSView *)hitTest:(NSPoint)point {
    return nil; // Pass all hit tests through to content below
}

- (void)mouseDown:(NSEvent *)event {
    self.lastMouseDownEvent = event;
    [self.window.contentView mouseDown:event];
}

- (void)mouseDragged:(NSEvent *)event {
    if (self.lastMouseDownEvent) {
        [self.window performWindowDragWithEvent:self.lastMouseDownEvent];
        self.lastMouseDownEvent = nil;
    }
}

- (void)mouseUp:(NSEvent *)event {
    self.lastMouseDownEvent = nil;
    [self.window.contentView mouseUp:event];
}

@end

// ─── MacosUIButtonsView ─────────────────────────────────────────────────────────
// Container for replacement traffic-light buttons in fullscreen.
// Propagates mouseEntered:/mouseExited: to all button subviews so AppKit
// activates the grouped traffic-light hover state (colored icons on hover).

@interface MacosUIButtonsView : NSView
@end

@implementation MacosUIButtonsView

- (void)updateTrackingAreas {
    [super updateTrackingAreas];
    for (NSTrackingArea *ta in self.trackingAreas) {
        [self removeTrackingArea:ta];
    }
    NSTrackingArea *ta = [[NSTrackingArea alloc]
        initWithRect:NSZeroRect
             options:(NSTrackingMouseEnteredAndExited |
                      NSTrackingActiveInKeyWindow |
                      NSTrackingInVisibleRect)
               owner:self
            userInfo:nil];
    [self addTrackingArea:ta];
}

- (void)mouseEntered:(NSEvent *)event {
    [super mouseEntered:event];
    for (NSView *btn in self.subviews) {
        [btn mouseEntered:event];
    }
}

- (void)mouseExited:(NSEvent *)event {
    [super mouseExited:event];
    for (NSView *btn in self.subviews) {
        [btn mouseExited:event];
    }
}

@end

// ─── MacosUIWindowObserver ───────────────────────────────────────────────────────
// Manages constraints and toolbar around fullscreen transitions, window
// activation, and appearance changes. Re-applies Auto Layout constraints
// whenever macOS may have recreated the titlebar view hierarchy.

@interface MacosUIWindowObserver : NSObject
@property (nonatomic, weak) NSWindow *window;
- (instancetype)initWithWindow:(NSWindow *)window;
@end

// Helper: re-apply constraints from stored height, skip if fullscreen.
static void reapplyTitleBarIfNeeded(NSWindow *window) {
    if (!window) return;
    if ((window.styleMask & NSWindowStyleMaskFullScreen) != 0) return;

    NSNumber *storedHeight = objc_getAssociatedObject(window, &kTitleBarHeightKey);
    float height = storedHeight ? [storedHeight floatValue] : kMinHeightForFullSize;

    // Ensure transparent titlebar properties are still set
    [window setTitlebarAppearsTransparent:YES];
    [window setTitleVisibility:NSWindowTitleHidden];

    // Reinstall toolbar if macOS removed it
    if (!window.toolbar) {
        NSToolbar *toolbar = [[NSToolbar alloc] initWithIdentifier:@"MacosUIToolbar"];
        toolbar.showsBaselineSeparator = NO;
        window.toolbar = toolbar;
    }

    applyConstraints(window, height);
}

@implementation MacosUIWindowObserver

- (instancetype)initWithWindow:(NSWindow *)window {
    self = [super init];
    if (self) {
        _window = window;
        NSNotificationCenter *nc = [NSNotificationCenter defaultCenter];
        [nc addObserver:self selector:@selector(willEnterFullScreen:)
                   name:NSWindowWillEnterFullScreenNotification object:window];
        [nc addObserver:self selector:@selector(didEnterFullScreen:)
                   name:NSWindowDidEnterFullScreenNotification object:window];
        [nc addObserver:self selector:@selector(willExitFullScreen:)
                   name:NSWindowWillExitFullScreenNotification object:window];
        [nc addObserver:self selector:@selector(didExitFullScreen:)
                   name:NSWindowDidExitFullScreenNotification object:window];
        [nc addObserver:self selector:@selector(windowDidBecomeKey:)
                   name:NSWindowDidBecomeKeyNotification object:window];
        [nc addObserver:self selector:@selector(windowDidChangeBackingProperties:)
                   name:NSWindowDidChangeBackingPropertiesNotification object:window];

        // KVO on effectiveAppearance — fires on dark/light mode switch
        [window addObserver:self
                 forKeyPath:@"effectiveAppearance"
                    options:0
                    context:NULL];
    }
    return self;
}

- (void)dealloc {
    [[NSNotificationCenter defaultCenter] removeObserver:self];
    NSWindow *w = self.window;
    if (w) {
        @try { [w removeObserver:self forKeyPath:@"effectiveAppearance"]; }
        @catch (NSException *e) { /* already removed */ }
    }
}

// About to enter fullscreen — remove constraints so macOS can animate cleanly
- (void)willEnterFullScreen:(NSNotification *)note {
    NSWindow *w = self.window;
    if (!w) return;

    removeExistingConstraints(w);
    // Remove toolbar before fullscreen animation to avoid white band glitch
    w.toolbar = nil;
    [w setTitlebarAppearsTransparent:NO];
    [w setTitleVisibility:NSWindowTitleVisible];
}

// Finished entering fullscreen — install replacement buttons in the content view
- (void)didEnterFullScreen:(NSNotification *)note {
    NSWindow *w = self.window;
    if (!w) return;

    NSNumber *storedHeight = objc_getAssociatedObject(w, &kTitleBarHeightKey);
    float height = storedHeight ? [storedHeight floatValue] : kMinHeightForFullSize;

    installFullScreenButtons(w, height);

    // Reinstall the toolbar (removed in willEnterFullScreen to avoid a white
    // band glitch during the animation) so 26pt corners show in fullscreen too.
    if (!w.toolbar) {
        NSToolbar *toolbar = [[NSToolbar alloc] initWithIdentifier:@"MacosUIToolbar"];
        toolbar.showsBaselineSeparator = NO;
        w.toolbar = toolbar;
    }
}

// About to exit fullscreen — remove replacement buttons, hide native traffic
// lights so they don't appear at the wrong position during the transition
- (void)willExitFullScreen:(NSNotification *)note {
    NSWindow *w = self.window;
    if (!w) return;

    removeFullScreenButtons(w);
    [w setTitlebarAppearsTransparent:YES];
    [w setTitleVisibility:NSWindowTitleHidden];

    // Hide standard buttons during transition to prevent position glitch
    [[w standardWindowButton:NSWindowCloseButton] setHidden:YES];
    [[w standardWindowButton:NSWindowMiniaturizeButton] setHidden:YES];
    [[w standardWindowButton:NSWindowZoomButton] setHidden:YES];
}

// Finished exiting fullscreen — restore constraints, then reveal the buttons
- (void)didExitFullScreen:(NSNotification *)note {
    NSWindow *w = self.window;
    if (!w) return;

    NSNumber *storedHeight = objc_getAssociatedObject(w, &kTitleBarHeightKey);
    if (!storedHeight) return;

    // Reinstall the invisible toolbar for 26pt corner radius
    if (!w.toolbar) {
        NSToolbar *toolbar = [[NSToolbar alloc] initWithIdentifier:@"MacosUIToolbar"];
        toolbar.showsBaselineSeparator = NO;
        w.toolbar = toolbar;
    }

    applyConstraints(w, [storedHeight floatValue]);

    // Reveal buttons now that constraints are in place
    [[w standardWindowButton:NSWindowCloseButton] setHidden:NO];
    [[w standardWindowButton:NSWindowMiniaturizeButton] setHidden:NO];
    [[w standardWindowButton:NSWindowZoomButton] setHidden:NO];
}

- (void)windowDidBecomeKey:(NSNotification *)note {
    reapplyTitleBarIfNeeded(self.window);
}

- (void)windowDidChangeBackingProperties:(NSNotification *)note {
    reapplyTitleBarIfNeeded(self.window);
}

- (void)observeValueForKeyPath:(NSString *)keyPath
                      ofObject:(id)object
                        change:(NSDictionary *)change
                       context:(void *)context {
    if ([keyPath isEqualToString:@"effectiveAppearance"]) {
        NSWindow *w = self.window;
        dispatch_async(dispatch_get_main_queue(), ^{
            if (!atomic_load(&sShutdownInProgress)) {
                reapplyTitleBarIfNeeded(w);
            }
        });
    }
}

@end

// ─── Zoom button responder ──────────────────────────────────────────────────────
// Re-enables movable when hovering the zoom button (macOS 15 tiling fix).

@interface MacosUIZoomResponder : NSObject
@property (nonatomic, weak) NSWindow *window;
@property (nonatomic, assign) BOOL savedMovable;
@end

@implementation MacosUIZoomResponder

- (void)mouseEntered:(NSEvent *)event {
    if (self.window) {
        self.savedMovable = self.window.movable;
        self.window.movable = YES;
    }
}

- (void)mouseExited:(NSEvent *)event {
    if (self.window) self.window.movable = self.savedMovable;
}

@end

// ─── _adjustWindowToScreen swizzle ──────────────────────────────────────────────
// Temporarily enables movable during system calls (tiling, Mission Control).

static IMP sOriginalAdjustWindowToScreen = NULL;
static BOOL sSwizzleApplied = NO;

static void swizzled_adjustWindowToScreen(id self, SEL _cmd) {
    NSNumber *storedHeight = objc_getAssociatedObject(self, &kTitleBarHeightKey);
    BOOL needsRestore = storedHeight && ![(NSWindow *)self isMovable];

    if (needsRestore) [(NSWindow *)self setMovable:YES];

    if (sOriginalAdjustWindowToScreen) {
        ((void (*)(id, SEL))sOriginalAdjustWindowToScreen)(self, _cmd);
    }

    updateFullScreenButtonsPosition((NSWindow *)self);

    if (needsRestore) [(NSWindow *)self setMovable:NO];
}

static void ensureAdjustWindowSwizzle(NSWindow *window __unused) {
    if (sSwizzleApplied) return;
    sSwizzleApplied = YES;

    SEL sel = NSSelectorFromString(@"_adjustWindowToScreen");
    Method m = class_getInstanceMethod([NSWindow class], sel);
    if (m) {
        sOriginalAdjustWindowToScreen = method_getImplementation(m);
        method_setImplementation(m, (IMP)swizzled_adjustWindowToScreen);
    }
}

// ─── Live resize observer ────────────────────────────────────────────────────────

static void setPresentsWithTransactionRecursive(NSView *view, BOOL value) {
    CALayer *layer = view.layer;
    if (layer && [layer isKindOfClass:[CAMetalLayer class]]) {
        ((CAMetalLayer *)layer).presentsWithTransaction = value;
    }
    for (NSView *subview in view.subviews) {
        setPresentsWithTransactionRecursive(subview, value);
    }
}

@interface MacosUIResizeObserverView : NSView
@end

@implementation MacosUIResizeObserverView

- (void)viewWillStartLiveResize {
    [super viewWillStartLiveResize];
    NSWindow *w = self.window;
    if (w && w.contentView) {
        setPresentsWithTransactionRecursive(w.contentView, YES);
    }
}

- (void)viewDidEndLiveResize {
    [super viewDidEndLiveResize];
    NSWindow *w = self.window;
    if (w && w.contentView) {
        setPresentsWithTransactionRecursive(w.contentView, NO);
    }
}

@end

static void ensureResizeObserver(NSWindow *window) {
    if (objc_getAssociatedObject(window, &kResizeObserverKey)) return;
    MacosUIResizeObserverView *observer = [[MacosUIResizeObserverView alloc]
        initWithFrame:NSZeroRect];
    observer.hidden = YES;
    [window.contentView addSubview:observer];
    objc_setAssociatedObject(window, &kResizeObserverKey, observer,
                             OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

static void removeResizeObserver(NSWindow *window) {
    MacosUIResizeObserverView *observer =
        objc_getAssociatedObject(window, &kResizeObserverKey);
    if (!observer) return;
    [observer removeFromSuperview];
    objc_setAssociatedObject(window, &kResizeObserverKey, nil,
                             OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

// ─── Helper functions ───────────────────────────────────────────────────────────

static void removeExistingConstraints(NSWindow *window) {
    NSArray *old = objc_getAssociatedObject(window, &kTitleBarConstraintsKey);
    if (old) {
        [NSLayoutConstraint deactivateConstraints:old];
        objc_setAssociatedObject(window, &kTitleBarConstraintsKey, nil,
                                 OBJC_ASSOCIATION_RETAIN_NONATOMIC);
    }
}

static void applyConstraints(NSWindow *window, float height) {
    NSView *closeBtn = [window standardWindowButton:NSWindowCloseButton];
    NSView *miniBtn  = [window standardWindowButton:NSWindowMiniaturizeButton];
    NSView *zoomBtn  = [window standardWindowButton:NSWindowZoomButton];
    if (!closeBtn || !miniBtn || !zoomBtn) return;

    NSView *titlebar          = closeBtn.superview;
    NSView *titlebarContainer = titlebar ? titlebar.superview : nil;
    NSView *themeFrame        = titlebarContainer ? titlebarContainer.superview : nil;
    if (!themeFrame) return;

    removeExistingConstraints(window);

    NSMutableArray *constraints = [NSMutableArray array];

    titlebarContainer.translatesAutoresizingMaskIntoConstraints = NO;
    [constraints addObjectsFromArray:@[
        [titlebarContainer.leftAnchor   constraintEqualToAnchor:themeFrame.leftAnchor],
        [titlebarContainer.widthAnchor  constraintEqualToAnchor:themeFrame.widthAnchor],
        [titlebarContainer.topAnchor    constraintEqualToAnchor:themeFrame.topAnchor],
        [titlebarContainer.heightAnchor constraintEqualToConstant:height],
    ]];

    titlebar.translatesAutoresizingMaskIntoConstraints = NO;
    [constraints addObjectsFromArray:@[
        [titlebar.leftAnchor   constraintEqualToAnchor:titlebarContainer.leftAnchor],
        [titlebar.rightAnchor  constraintEqualToAnchor:titlebarContainer.rightAnchor],
        [titlebar.topAnchor    constraintEqualToAnchor:titlebarContainer.topAnchor],
        [titlebar.bottomAnchor constraintEqualToAnchor:titlebarContainer.bottomAnchor],
    ]];

    // Drag view constraints
    MacosUIDragView *dragView = objc_getAssociatedObject(window, &kDragViewKey);
    if (dragView) {
        dragView.translatesAutoresizingMaskIntoConstraints = NO;
        [constraints addObjectsFromArray:@[
            [dragView.leftAnchor   constraintEqualToAnchor:titlebarContainer.leftAnchor],
            [dragView.rightAnchor  constraintEqualToAnchor:titlebarContainer.rightAnchor],
            [dragView.topAnchor    constraintEqualToAnchor:titlebarContainer.topAnchor],
            [dragView.bottomAnchor constraintEqualToAnchor:titlebarContainer.bottomAnchor],
        ]];
    }

    float shrinkFactor = fminf(height / kMinHeightForFullSize, 1.0f);
    float offset       = shrinkFactor * kDefaultButtonOffset;
    float extraInset   = window.toolbar ? kToolbarExtraInset : 0.0f;
    float margin       = fminf(height / 2.0f, kMaxButtonLeftMargin) + extraInset;

    NSLayoutAnchor *anchorEdge = titlebarContainer.leftAnchor;

    NSArray *buttons = @[closeBtn, miniBtn, zoomBtn];
    [buttons enumerateObjectsUsingBlock:^(NSView *btn, NSUInteger idx, BOOL *stop) {
        btn.translatesAutoresizingMaskIntoConstraints = NO;
        float c = margin + idx * offset;
        [constraints addObjectsFromArray:@[
            [btn.widthAnchor   constraintLessThanOrEqualToAnchor:titlebarContainer.heightAnchor
                                                      multiplier:0.5],
            [btn.heightAnchor  constraintEqualToAnchor:btn.widthAnchor
                                            multiplier:14.0 / 12.0
                                              constant:-2.0],
            [btn.centerYAnchor constraintEqualToAnchor:titlebarContainer.topAnchor
                                              constant:height / 2.0f],
            [btn.centerXAnchor constraintEqualToAnchor:anchorEdge
                                              constant:c],
        ]];
    }];

    [NSLayoutConstraint activateConstraints:constraints];
    objc_setAssociatedObject(window, &kTitleBarConstraintsKey, constraints,
                             OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

static void ensureDragView(NSWindow *window) {
    if (objc_getAssociatedObject(window, &kDragViewKey)) return;

    NSView *closeBtn = [window standardWindowButton:NSWindowCloseButton];
    NSView *titlebar = closeBtn.superview;
    if (!titlebar) return;

    MacosUIDragView *dragView = [[MacosUIDragView alloc] initWithFrame:titlebar.bounds];
    dragView.autoresizingMask = NSViewWidthSizable | NSViewHeightSizable;
    [titlebar addSubview:dragView];
    objc_setAssociatedObject(window, &kDragViewKey, dragView,
                             OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

static void removeDragView(NSWindow *window) {
    MacosUIDragView *dragView = objc_getAssociatedObject(window, &kDragViewKey);
    if (!dragView) return;
    [dragView removeFromSuperview];
    objc_setAssociatedObject(window, &kDragViewKey, nil, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

static void ensureWindowObserver(NSWindow *window) {
    if (objc_getAssociatedObject(window, &kFullscreenObserverKey)) return;
    MacosUIWindowObserver *obs = [[MacosUIWindowObserver alloc] initWithWindow:window];
    objc_setAssociatedObject(window, &kFullscreenObserverKey, obs,
                             OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

static void installZoomButtonResponder(NSWindow *window) {
    if (objc_getAssociatedObject(window, &kZoomResponderKey)) return;

    NSButton *zoomBtn = [window standardWindowButton:NSWindowZoomButton];
    if (!zoomBtn) return;

    MacosUIZoomResponder *responder = [[MacosUIZoomResponder alloc] init];
    responder.window = window;

    NSTrackingArea *area = [[NSTrackingArea alloc]
        initWithRect:zoomBtn.bounds
             options:(NSTrackingMouseEnteredAndExited | NSTrackingActiveAlways | NSTrackingInVisibleRect)
               owner:responder
            userInfo:nil];
    [zoomBtn addTrackingArea:area];
    objc_setAssociatedObject(window, &kZoomResponderKey, responder,
                             OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

// ─── Fullscreen button helpers ──────────────────────────────────────────────────

// Hides the native NSToolbarFullScreenWindow so the system hover toolbar
// doesn't overlap with our replacement buttons.
static void hideToolbarFullScreenWindow(void) {
    for (NSWindow *win in [[NSApplication sharedApplication] windows]) {
        if ([win isKindOfClass:NSClassFromString(@"NSToolbarFullScreenWindow")]) {
            [win.contentView setHidden:YES];
        }
    }
}

// Computes button size and positions matching the constraint-based layout
// used in floating mode (applyConstraints), so there is no visual jump
// when transitioning between fullscreen and floating.
static void computeButtonMetrics(float titleBarHeight, float *outBtnWidth, float *outBtnHeight, float *outOffset) {
    float shrinkFactor = fminf(titleBarHeight / kMinHeightForFullSize, 1.0f);
    *outBtnWidth  = fminf(titleBarHeight * 0.5f, kMinHeightForFullSize * 0.5f);
    *outBtnHeight = (*outBtnWidth) * (14.0f / 12.0f) - 2.0f;
    *outOffset    = shrinkFactor * kDefaultButtonOffset;
}

// Creates replacement traffic-light buttons in the content view.
// Button positions match the constraint-based layout used in floating mode.
static void installFullScreenButtons(NSWindow *window, float titleBarHeight) {
    if (objc_getAssociatedObject(window, &kFullscreenButtonsKey)) return;

    NSView *origClose = [window standardWindowButton:NSWindowCloseButton];
    if (!origClose) return;

    hideToolbarFullScreenWindow();

    float btnWidth, btnHeight, offset;
    computeButtonMetrics(titleBarHeight, &btnWidth, &btnHeight, &offset);

    MacosUIButtonsView *container = [[MacosUIButtonsView alloc] init];
    NSView *parent = window.contentView;
    CGFloat y = parent.frame.size.height - titleBarHeight;
    float margin = fminf(titleBarHeight / 2.0f, kMaxButtonLeftMargin) + kToolbarExtraInset;
    float containerWidth = margin + 2.0f * offset + btnWidth;
    [container setFrame:NSMakeRect(0, y, containerWidth, titleBarHeight)];

    NSUInteger masks = [window styleMask];

    NSArray<NSNumber *> *buttonTypes = @[
        @(NSWindowCloseButton), @(NSWindowMiniaturizeButton), @(NSWindowZoomButton)
    ];
    SEL actions[] = { @selector(performClose:), @selector(performMiniaturize:), @selector(toggleFullScreen:) };

    for (NSUInteger idx = 0; idx < 3; idx++) {
        NSButton *btn = [NSWindow standardWindowButton:[buttonTypes[idx] unsignedIntegerValue]
                                          forStyleMask:masks];
        CGFloat centerX = margin + idx * offset;
        CGFloat centerY = titleBarHeight / 2.0f;
        [btn setFrame:NSMakeRect(centerX - btnWidth / 2.0f, centerY - btnHeight / 2.0f,
                                 btnWidth, btnHeight)];
        [btn setTarget:window];
        [btn setAction:actions[idx]];
        [container addSubview:btn];
    }

    [parent addSubview:container];

    objc_setAssociatedObject(window, &kFullscreenButtonsKey, container,
                             OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

// Removes the replacement fullscreen buttons.
static void removeFullScreenButtons(NSWindow *window) {
    MacosUIButtonsView *container = objc_getAssociatedObject(window, &kFullscreenButtonsKey);
    if (!container) return;

    [container removeFromSuperview];
    objc_setAssociatedObject(window, &kFullscreenButtonsKey, nil,
                             OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

// Repositions the fullscreen button container (called from layout passes).
static void updateFullScreenButtonsPosition(NSWindow *window) {
    MacosUIButtonsView *container = objc_getAssociatedObject(window, &kFullscreenButtonsKey);
    if (!container) return;

    NSView *parent = window.contentView;
    if (!parent) return;

    NSNumber *storedHeight = objc_getAssociatedObject(window, &kTitleBarHeightKey);
    float titleBarHeight = storedHeight ? [storedHeight floatValue] : kMinHeightForFullSize;

    float btnWidth, btnHeight, offset;
    computeButtonMetrics(titleBarHeight, &btnWidth, &btnHeight, &offset);

    float margin = fminf(titleBarHeight / 2.0f, kMaxButtonLeftMargin) + kToolbarExtraInset;
    float containerWidth = margin + 2.0f * offset + btnWidth;
    CGFloat y = parent.frame.size.height - titleBarHeight;
    [container setFrame:NSMakeRect(0, y, containerWidth, titleBarHeight)];

    // Reposition each button inside the container
    NSArray<NSView *> *buttons = [container subviews];
    for (NSUInteger idx = 0; idx < buttons.count && idx < 3; idx++) {
        NSView *btn = buttons[idx];
        CGFloat centerX = margin + idx * offset;
        CGFloat centerY = titleBarHeight / 2.0f;
        [btn setFrame:NSMakeRect(centerX - btnWidth / 2.0f, centerY - btnHeight / 2.0f,
                                 btnWidth, btnHeight)];
    }
}

// ─── NSWindow pointer extraction ────────────────────────────────────────────────

static jlong getNSWindowPtrFromAWTWindow(JNIEnv *env, jobject awtWindow) {
    // AWT Component → peer
    jclass componentClass = (*env)->FindClass(env, "java/awt/Component");
    if (!componentClass) return 0;
    jfieldID peerField = (*env)->GetFieldID(env, componentClass, "peer", "Ljava/awt/peer/ComponentPeer;");
    if (!peerField) { (*env)->ExceptionClear(env); return 0; }
    jobject peer = (*env)->GetObjectField(env, awtWindow, peerField);
    if (!peer) return 0;

    // peer → getPlatformWindow()
    jclass peerClass = (*env)->GetObjectClass(env, peer);
    jmethodID getPlatformWindow = (*env)->GetMethodID(env, peerClass, "getPlatformWindow",
        "()Lsun/lwawt/PlatformWindow;");
    if (!getPlatformWindow) { (*env)->ExceptionClear(env); return 0; }
    jobject platformWindow = (*env)->CallObjectMethod(env, peer, getPlatformWindow);
    if (!platformWindow) return 0;

    // Walk superclass chain for "ptr" field
    jclass pwClass = (*env)->GetObjectClass(env, platformWindow);
    jfieldID ptrField = NULL;
    while (pwClass && !ptrField) {
        ptrField = (*env)->GetFieldID(env, pwClass, "ptr", "J");
        if (ptrField) break;
        (*env)->ExceptionClear(env);
        pwClass = (*env)->GetSuperclass(env, pwClass);
    }
    if (!ptrField) return 0;
    return (*env)->GetLongField(env, platformWindow, ptrField);
}

// ─── JNI exports ────────────────────────────────────────────────────────────────

JNIEXPORT jlong JNICALL
Java_io_github_kdroidfilter_nucleus_ui_apple_macos_window_MacosWindowBridge_nativeGetNSWindowPtr(
    JNIEnv *env, jclass clazz, jobject awtWindow) {
    return getNSWindowPtrFromAWTWindow(env, awtWindow);
}

JNIEXPORT jfloat JNICALL
Java_io_github_kdroidfilter_nucleus_ui_apple_macos_window_MacosWindowBridge_nativeApplyTitleBar(
    JNIEnv *env, jclass clazz, jlong nsWindowPtr, jfloat heightPt) {

    if (nsWindowPtr == 0) return 0.0f;

    // Compute left inset synchronously (returned to caller)
    float shrink     = fminf(heightPt / kMinHeightForFullSize, 1.0f);
    float btnOffset  = shrink * kDefaultButtonOffset;
    float leftMargin = fminf(heightPt / 2.0f, kMaxButtonLeftMargin) + kToolbarExtraInset;
    float leftInset  = 2.0f * leftMargin + 2.0f * btnOffset;
    float capturedHeight = heightPt;

    void *rawPtr = (void *)nsWindowPtr;
    dispatch_async(dispatch_get_main_queue(), ^{
        if (atomic_load(&sShutdownInProgress)) return;
        @autoreleasepool {
            NSWindow *w = nil;
            for (NSWindow *win in [NSApp windows]) {
                if ((__bridge void *)win == rawPtr) { w = win; break; }
            }
            if (!w) return;

            objc_setAssociatedObject(w, &kTitleBarHeightKey,
                                     @(capturedHeight), OBJC_ASSOCIATION_RETAIN_NONATOMIC);

            ensureWindowObserver(w);
            ensureAdjustWindowSwizzle(w);
            installZoomButtonResponder(w);

            if ((w.styleMask & NSWindowStyleMaskFullScreen) != 0) {
                // In fullscreen: update replacement button positions
                updateFullScreenButtonsPosition(w);
                return;
            }

            [w setTitlebarAppearsTransparent:YES];
            [w setTitleVisibility:NSWindowTitleHidden];
            ensureResizeObserver(w);

            // Always install invisible toolbar for 26pt corner radius
            if (!w.toolbar) {
                NSToolbar *toolbar = [[NSToolbar alloc] initWithIdentifier:@"MacosUIToolbar"];
                toolbar.showsBaselineSeparator = NO;
                w.toolbar = toolbar;
            }

            applyConstraints(w, capturedHeight);
        }
    });

    return leftInset;
}

JNIEXPORT void JNICALL
Java_io_github_kdroidfilter_nucleus_ui_apple_macos_window_MacosWindowBridge_nativeResetTitleBar(
    JNIEnv *env, jclass clazz, jlong nsWindowPtr) {

    if (nsWindowPtr == 0) return;
    void *rawPtr = (void *)nsWindowPtr;
    dispatch_async(dispatch_get_main_queue(), ^{
        if (atomic_load(&sShutdownInProgress)) return;
        @autoreleasepool {
            NSWindow *w = nil;
            for (NSWindow *win in [NSApp windows]) {
                if ((__bridge void *)win == rawPtr) { w = win; break; }
            }
            if (!w) return;

            removeExistingConstraints(w);
            removeResizeObserver(w);
            removeFullScreenButtons(w);
            removeDragView(w);

            // Remove window observer (also removes its KVO)
            objc_setAssociatedObject(w, &kFullscreenObserverKey, nil,
                                     OBJC_ASSOCIATION_RETAIN_NONATOMIC);

            // Remove zoom responder
            objc_setAssociatedObject(w, &kZoomResponderKey, nil,
                                     OBJC_ASSOCIATION_RETAIN_NONATOMIC);

            // Restore movable in case zoom responder left it disabled
            w.movable = YES;

            // Remove toolbar and restore defaults
            w.toolbar = nil;
            [w setTitlebarAppearsTransparent:NO];
            [w setTitleVisibility:NSWindowTitleVisible];
        }
    });
}

JNIEXPORT void JNICALL
Java_io_github_kdroidfilter_nucleus_ui_apple_macos_window_MacosWindowBridge_nativeRevalidateTitleBar(
    JNIEnv *env, jclass clazz, jlong nsWindowPtr) {

    if (nsWindowPtr == 0) return;
    void *rawPtr = (void *)nsWindowPtr;
    dispatch_async(dispatch_get_main_queue(), ^{
        if (atomic_load(&sShutdownInProgress)) return;
        @autoreleasepool {
            NSWindow *w = nil;
            for (NSWindow *win in [NSApp windows]) {
                if ((__bridge void *)win == rawPtr) { w = win; break; }
            }
            if (!w) return;
            reapplyTitleBarIfNeeded(w);
        }
    });
}

JNIEXPORT void JNICALL
Java_io_github_kdroidfilter_nucleus_ui_apple_macos_window_MacosWindowBridge_nativePerformTitleBarDoubleClick(
    JNIEnv *env, jclass clazz, jlong nsWindowPtr) {

    if (nsWindowPtr == 0) return;
    void *rawPtr = (void *)nsWindowPtr;
    dispatch_async(dispatch_get_main_queue(), ^{
        if (atomic_load(&sShutdownInProgress)) return;
        @autoreleasepool {
            NSWindow *w = nil;
            for (NSWindow *win in [NSApp windows]) {
                if ((__bridge void *)win == rawPtr) { w = win; break; }
            }
            if (!w) return;

            NSString *action = [[NSUserDefaults standardUserDefaults]
                                stringForKey:@"AppleActionOnDoubleClick"];
            // Default macOS behavior is zoom when no preference is set
            if (!action || [action isEqualToString:@"Maximize"]) {
                [w zoom:nil];
            } else if ([action isEqualToString:@"Minimize"]) {
                [w miniaturize:nil];
            }
            // "None" — do nothing
        }
    });
}

// Shutdown hook — prevent dispatch_async blocks from accessing deallocated windows
__attribute__((destructor))
static void onUnload(void) {
    atomic_store(&sShutdownInProgress, true);
}
