# Darwin UI

A macOS-inspired UI component library for Compose Multiplatform with full light/dark theme support.

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-7F52FF.svg?logo=kotlin)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-1.10.0-4285F4.svg?logo=jetpackcompose)](https://www.jetbrains.com/compose-multiplatform/)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Commercial License](https://img.shields.io/badge/License-Commercial-green.svg)](#commercial-license--20)

## Features

- **30+ components** — Buttons, Cards, Dialogs, Tables, Toasts, and more
- **Light & Dark mode** — Theme-aware styling with automatic system detection
- **macOS aesthetic** — Clean, native-feeling design inspired by Apple's design language
- **Compose Multiplatform** — Runs on Android, iOS, Desktop (JVM), and Web (JS/Wasm)
- **Built-in design system** — Colors, typography (Manrope), shapes, and animations out of the box
- **No external UI dependencies** — Pure Compose, no Material dependency required

## Supported Platforms

| Platform | Status |
|----------|--------|
| Android  | ✓      |
| iOS      | ✓      |
| Desktop (JVM) | ✓ |
| Web (JS) | ✓      |
| Web (Wasm) | ✓    |

## Quick Start

### 1. Add the dependency

```kotlin
// build.gradle.kts
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.kdroidfilter:darwinui:<version>")
        }
    }
}
```

### 2. Wrap your app with DarwinTheme

```kotlin
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
fun App() {
    DarwinTheme(darkTheme = false) {
        // Your content here
        // Access design tokens via DarwinTheme.colors, DarwinTheme.typography, etc.
    }
}
```

### 3. Use components

```kotlin
import io.github.kdroidfilter.darwinui.components.button.DarwinButton
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonVariant
import io.github.kdroidfilter.darwinui.components.card.*
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
fun MyScreen() {
    DarwinCard {
        DarwinCardHeader {
            DarwinCardTitle("Welcome to Darwin UI")
            DarwinCardDescription("A macOS-inspired component library")
        }
        DarwinCardContent {
            DarwinButton(
                onClick = { /* ... */ },
                variant = DarwinButtonVariant.Primary,
            ) {
                DarwinText("Get Started")
            }
        }
    }
}
```

## Components

### Form Controls

| Component | Description |
|-----------|-------------|
| **Button** | 11 variants — Default, Primary, Secondary, Success, Warning, Info, Destructive, Outline, Ghost, Link, Accent |
| **Input** | Text field with label, placeholder, and validation states |
| **TextArea** | Multi-line text input |
| **SearchField** | Search input with icon |
| **Checkbox** | Checkbox with label support |
| **Switch** | Toggle switch |
| **Select** | Single-selection dropdown |
| **MultiSelect** | Multi-selection dropdown |
| **Slider** | Range slider input |
| **DateSelect** | Date picker |
| **Upload** | File upload component |

### Data Display

| Component | Description |
|-----------|-------------|
| **Card** | Composable card with header, content, footer, title, and description |
| **Badge** | Status badges with semantic color variants |
| **Avatar** | User avatar with image or initials fallback |
| **Table** | Full table system with headers and rows |
| **Progress** | Linear progress indicator |
| **Skeleton** | Loading placeholder animations |

### Feedback

| Component | Description |
|-----------|-------------|
| **Alert** | Alert banners with type indicators |
| **Toast** | Toast notifications with auto-dismiss and positioning |

### Overlays

| Component | Description |
|-----------|-------------|
| **Dialog** | Modal dialog with header, content, and footer |
| **Tooltip** | Hover tooltips |
| **Popover** | Floating popover content |
| **DropdownMenu** | Dropdown menu with items and separators |
| **ContextMenu** | Right-click context menu |

### Navigation

| Component | Description |
|-----------|-------------|
| **Tabs** | Tabbed content navigation |
| **Accordion** | Collapsible content sections |
| **Sidebar** | Navigation sidebar with groups, icons, and collapsible state |

### Utility

| Component | Description |
|-----------|-------------|
| **Text** | Theme-aware text component |
| **Spinner** | Loading spinner |
| **Icon** | Icon rendering component (includes Lucide icons) |

## Design System

Darwin UI provides a complete design system accessible through `DarwinTheme`:

```kotlin
// Colors
DarwinTheme.colors.background
DarwinTheme.colors.textPrimary
DarwinTheme.colors.border
DarwinTheme.colors.primary

// Typography (Manrope font family)
DarwinTheme.typography.headingLarge
DarwinTheme.typography.bodyMedium
DarwinTheme.typography.bodySmall

// Shapes
DarwinTheme.shapes.small       // 8dp
DarwinTheme.shapes.medium      // 10dp
DarwinTheme.shapes.large       // 12dp
DarwinTheme.shapes.extraLarge  // 16dp

// Animations
DarwinTheme.animations
```

### Dark Mode

Dark mode is supported out of the box. Pass `darkTheme` to `DarwinTheme`, or let it follow the system setting automatically:

```kotlin
// Follow system setting (default)
DarwinTheme {
    // ...
}

// Force dark mode
DarwinTheme(darkTheme = true) {
    // ...
}

// Manual toggle
var isDark by remember { mutableStateOf(false) }
DarwinTheme(darkTheme = isDark) {
    DarwinButton(onClick = { isDark = !isDark }) {
        DarwinText(if (isDark) "Light Mode" else "Dark Mode")
    }
}
```

## Running the Sample App

The project includes a gallery app that showcases all components.

```bash
# Desktop (JVM)
./gradlew :sample:run

# Web (Wasm)
./gradlew :sample:wasmJsBrowserDevelopmentRun

# Web (JS)
./gradlew :sample:jsBrowserDevelopmentRun

# Android
./gradlew :sample:assembleDebug
```

## Project Structure

```
DarwinUI/
├── darwinui/          # Library module — all components, theme, icons
├── sample/            # Gallery app showcasing every component
├── gallery-annotations/  # KSP annotation for gallery examples
└── gallery-ksp/       # KSP processor for gallery source extraction
```

## License

Darwin UI is available under a **dual license**:

### Open Source — GPL v3

Free for open-source projects. If your project is distributed under a GPL-compatible license, you can use Darwin UI at no cost under the terms of the [GNU General Public License v3.0](LICENSE).

### Commercial License — $20

For proprietary/closed-source projects, a commercial license is available for **$20** (one-time payment). This license covers usage in **up to 5 commercial projects**.

[Purchase a commercial license](https://github.com/kdroidFilter/DarwinUI) <!-- TODO: update link -->

If you have questions about licensing, feel free to open an issue.
