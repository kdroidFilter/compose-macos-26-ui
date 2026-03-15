# Getting Started

Add Nucleus UI to your Kotlin Multiplatform project.

## Installation

Add the dependency to your `build.gradle.kts`:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.kdroidfilter:compose-macos-ui:<version>")
        }
    }
}
```

### Optional modules

```kotlin
// Extended icon set (Lucide icons)
implementation("io.github.kdroidfilter:compose-macos-ui-icons-extended:<version>")

// macOS-themed Markdown renderer
implementation("io.github.kdroidfilter:compose-macos-ui-markdown:<version>")
```

## Setup

Wrap your app content with `MacosTheme`:

```kotlin
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.AccentColor

@Composable
fun App() {
    MacosTheme(
        darkTheme = isSystemInDarkTheme(),
        accentColor = AccentColor.Blue,
    ) {
        // Your content here
    }
}
```

## Your first component

```kotlin
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PushButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text

@Composable
fun HelloNucleus() {
    PushButton(onClick = { /* ... */ }) {
        Text("Hello, Nucleus UI!")
    }
}
```

## Accent Colors

Nucleus UI supports all macOS accent colors:

`Blue` · `Purple` · `Violet` · `Green` · `Orange` · `Red` · `Yellow` · `Cyan` · `Pink` · `Teal` · `Emerald` · `Sky`

```kotlin
MacosTheme(accentColor = AccentColor.Purple) {
    // All components use the purple accent
}
```

## Typography

The type scale follows the **macOS Human Interface Guidelines**:

| Style | Usage |
|---|---|
| `largeTitle` | Main screen titles |
| `title1` / `title2` / `title3` | Section headings |
| `headline` | Emphasized body text |
| `body` | Default text |
| `callout` | Secondary descriptions |
| `subheadline` | Tertiary text |
| `footnote` | Footnotes, timestamps |
| `caption1` / `caption2` | Labels, badges |

```kotlin
Text(
    text = "Hello",
    style = MacosTheme.typography.headline,
    color = MacosTheme.colorScheme.textPrimary,
)
```

## Control Sizes

macOS controls come in five sizes, following the [Apple Human Interface Guidelines](https://developer.apple.com/design/human-interface-guidelines/layout). Used together, they create hierarchy and balance across complex desktop layouts.

| Size | Shape | When to use |
|---|---|---|
| `Mini` | Rounded rect | Ultra-compact, high-density layouts — inspector panels, popovers, settings toggles. |
| `Small` | Rounded rect | Space-constrained views — toolbars, sidebars, dense forms, list row accessories. |
| `Regular` | Rounded rect | **Default.** Standard windows, dialogs, and main content areas. Use this unless you have a specific reason not to. |
| `Large` | Capsule | Standout actions that need visual prominence — primary call-to-action buttons, onboarding flows. |
| `ExtraLarge` | Capsule | The most emphasized action on screen — hero-level buttons, full-screen landing states, first-run experiences. |

```kotlin
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize

// Scope all children to Small
ControlSize(ControlSize.Small) {
    PushButton(onClick = { }) { Text("Small Button") }
    CheckBox(checked = true, onCheckedChange = {})
}
```

> **Tip:** `ControlSize` is inherited via `CompositionLocal`. Set it once on a container and all components inside will adapt automatically — buttons, inputs, checkboxes, switches, etc.

## Platforms

Nucleus UI targets all Compose Multiplatform platforms:

- **Android** (minSdk 24)
- **iOS** (Arm64 + Simulator)
- **Desktop JVM**
- **Web** (JS + WASM)

## Links

- [GitHub Repository](https://github.com/kdroidFilter/compose-macos-26-ui)
- [Author — Elie Gambache](https://eliegambache.kdroidfilter.com/)
