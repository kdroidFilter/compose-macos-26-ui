# ComposeDarwinUI — Plan de développement

Comparaison avec Jewel (IntelliJ Compose theme) et composants macOS natifs manquants.

---

## Phase 1 — Fondations architecturales ✅ COMPLÈTE

### 1.1 Système Style par composant
Actuellement chaque composant n'a que des `*Colors`. Jewel encapsule Colors + Metrics + Icons dans un objet `*Style` unique.

- [x] Créer `ButtonStyle(colors, metrics)` — `theme/DarwinComponentStyles.kt`
- [x] Créer `TextFieldStyle(colors, metrics)` — `theme/DarwinComponentStyles.kt`
- [x] Créer `CheckboxStyle(colors, metrics)` — `theme/DarwinComponentStyles.kt`
- [x] Créer `RadioButtonStyle(colors, metrics)` — `theme/DarwinComponentStyles.kt`
- [x] Créer `SwitchStyle(colors, metrics)` — `theme/DarwinComponentStyles.kt`
- [x] Créer `SliderStyle(colors, metrics)` — `theme/DarwinComponentStyles.kt`
- [x] Créer `TabStyle(colors, metrics)` — `theme/DarwinComponentStyles.kt`
- [x] Créer `ComboBoxStyle(colors, metrics)` — `theme/DarwinComponentStyles.kt`
- [x] Créer `SegmentedControlStyle(colors, metrics)` — `theme/DarwinComponentStyles.kt`
- [x] Créer `TooltipStyle(colors, metrics)` — `theme/DarwinComponentStyles.kt`
- [x] Créer `CardStyle(colors, metrics)` — `theme/DarwinComponentStyles.kt`
- [x] Créer `ComponentStyling` registry + `defaultComponentStyling()` — `theme/DarwinComponentStyling.kt`
- [x] Exposer via `DarwinTheme.componentStyling` + `LocalDarwinComponentStyling`
- [x] Migrer les Defaults existants vers ce pattern — `TextFieldDefaults` et `CheckboxDefaults` lisent depuis `DarwinTheme.componentStyling`

### 1.2 Hiérarchie d'états d'interaction
Jewel définit une hiérarchie claire : InteractiveComponentState → FocusableComponentState / SelectableComponentState / ToggleableComponentState.

- [x] `InteractiveComponentState` (isActive, isEnabled, isHovered, isPressed) — `theme/DarwinComponentStates.kt`
- [x] `FocusableComponentState` extends interactive + isFocused — `theme/DarwinComponentStates.kt`
- [x] `SelectableComponentState` extends interactive + isSelected — `theme/DarwinComponentStates.kt`
- [x] `FocusableSelectableComponentState` (combo) — `theme/DarwinComponentStates.kt`
- [x] `ToggleableComponentState` avec toggleableState (on/off/indeterminate) — `theme/DarwinComponentStates.kt`
- [x] Intégrer dans les composants existants — `TextFieldState : FocusableComponentState`, `CheckboxState : ToggleableComponentState` ajoutés

### 1.3 GlobalColors et GlobalMetrics
- [x] `GlobalColors` — borders (normal/disabled/focused/strong), outlines (focused/warning/error variants), semantic content tints — `theme/DarwinGlobalColors.kt`
- [x] `GlobalMetrics` — outlineWidth, borderWidth, component heights (mini/small/regular/large), cornerRadii, spacing — `theme/DarwinGlobalMetrics.kt`
- [x] Exposer via `DarwinTheme.globalColors` / `DarwinTheme.globalMetrics`
- [x] Provided in `DarwinTheme` via `LocalDarwinGlobalColors` / `LocalDarwinGlobalMetrics`

### 1.4 Système Outline / Validation
- [x] `Outline` enum : None, Warning, Error — `theme/DarwinOutline.kt`
- [x] `Modifier.focusOutline(shape, color, outlineWidth)` — focus ring macOS standardisé
- [x] `Modifier.outline(outline, shape, outlines)` — outline de validation
- [x] `Modifier.focusOrValidationOutline(isFocused, outline, shape, outlines)` — combiné
- [x] Intégrer dans TextField, TextArea, ComboBox, SearchField — `outline: Outline` param + `focusOrValidationOutline`

### 1.5 Typography macOS
Remplacer les styles Material-like par la vraie échelle typographique macOS :
- [x] LargeTitle (34sp Bold) — `theme/DarwinTypography.kt`
- [x] Title1 (28sp), Title2 (22sp), Title3 (20sp) — `theme/DarwinTypography.kt`
- [x] Headline (17sp SemiBold), Body (17sp), Callout (16sp) — `theme/DarwinTypography.kt`
- [x] Subheadline (15sp), Footnote (13sp), Caption1 (12sp), Caption2 (11sp) — `theme/DarwinTypography.kt`
- [x] Conserver les noms Material pour compatibilité (displayLarge, bodyMedium, etc.)

---

## Phase 2 — Composants manquants (priorité haute)

### 2.1 Variantes de boutons
Implémentés (nommage macOS natif, pas Jewel) :
- [x] `PushButton` — bouton standard neutre (bezel/rounded) — `components/Button.kt`
- [x] `PulldownButton` — popup/pulldown flat style — `components/Button.kt`
- [x] `GlassPulldownButton` — pulldown avec fond glass + shadow — `components/Button.kt`
- [x] `DisclosureButton` — toggle circulaire expand/collapse — `components/Button.kt`
- [x] `ArrowButton` — stepper NSStepper-like (haut/bas) — `components/Button.kt`
- [x] `PanelAccentButton` / `PanelDestructiveButton` / `PanelSecondaryButton` — boutons footer NSSavePanel — `components/Button.kt`
- [x] `MacNativeAccentButton` / `MacNativeDestructiveButton` / `MacNativeSecondaryButton` — boutons pill alert — `components/Button.kt`

Manquants :
- [ ] `OutlinedButton` — bouton avec bordure visible, fond transparent
- [ ] `TintedButton` — bouton avec teinte accent (fond coloré semi-transparent)
- [ ] `PlainButton` — bouton texte sans fond (lien-like)
- [ ] `SplitButton` — action principale + flèche dropdown séparée

### 2.2 Link
- [ ] `Link(text, onClick)` — lien cliquable stylisé
- [ ] `ExternalLink(text, url)` — lien externe avec icône
- [ ] `DropdownLink(text, onOpen)` — lien avec dropdown

### 2.3 LazyTree (NSOutlineView)
- [ ] `LazyTree(tree, onSelect)` — arbre hiérarchique lazy-loaded
- [ ] `Tree<T>` data structure (Leaf / Node)
- [ ] Disclosure triangles macOS animés
- [ ] Sélection simple et multiple
- [ ] Keyboard navigation (flèches, espace, enter)
- [ ] Page sample

### 2.4 SplitLayout (NSSplitView)
- [ ] `HorizontalSplitLayout(first, second, state)` — panneaux côte à côte
- [ ] `VerticalSplitLayout(first, second, state)` — panneaux empilés
- [ ] `SplitLayoutState` — position du diviseur, min/max
- [ ] `rememberSplitLayoutState(initialPosition)`
- [ ] Thin divider macOS style
- [ ] Page sample

### 2.5 Scrollbars macOS
- [ ] `VerticalScrollbar(scrollState, style)` — overlay scrollbar macOS
- [ ] `HorizontalScrollbar(scrollState, style)`
- [ ] Auto-hide avec animation fade
- [ ] Style overlay (thin, superposé au contenu)
- [ ] `ScrollbarStyle(colors, metrics)`

### 2.6 SelectableLazyColumn
- [ ] `SelectableLazyColumn(state, selectionMode, content)`
- [ ] `SelectableLazyListState` — gestion sélection
- [ ] `SelectionMode` enum : None, Single, Multiple
- [ ] Keyboard navigation (flèches, shift+click, cmd+click)
- [ ] `SimpleListItem(text, icon, selected, onClick)`

### 2.7 Divider
- [ ] `Divider(modifier, orientation, color, thickness)`
- [ ] Support horizontal et vertical

### 2.8 Modifier.trackWindowActivation
- [ ] `Modifier.trackWindowActivation()` — détecte fenêtre active/inactive
- [ ] `LocalWindowActive` CompositionLocal
- [ ] Composants adaptent leur apparence quand fenêtre inactive (grisé macOS)

---

## Phase 3 — Composants manquants (priorité moyenne)

### 3.1 Chips / Tags
- [ ] `Chip(label, onClick, onDismiss)` — chip basique
- [ ] `ToggleableChip(selected, onToggle, label)` — chip toggle
- [ ] `RadioButtonChip` — chip radio exclusive
- [ ] `ChipStyle(colors, metrics)`

### 3.2 Banner
- [ ] `InformationBanner(text, actions)` — bannière info
- [ ] `SuccessBanner(text, actions)` — bannière succès
- [ ] `WarningBanner(text, actions)` — bannière warning
- [ ] `ErrorBanner(text, actions)` — bannière erreur
- [ ] Variantes inline et default

### 3.3 Boutons icône avancés
- [ ] `SelectableIconButton(selected, onClick, icon)` — toggle sélection
- [ ] `ToggleableIconButton(toggled, onToggle, icon)` — toggle on/off
- [ ] `ActionButton(onClick, icon, tooltip)` — icône + tooltip intégré

### 3.4 CheckboxRow / RadioButtonRow
- [ ] `CheckboxRow(checked, onCheckedChange, label)` — checkbox + label en row
- [ ] `TriStateCheckboxRow(state, onClick, label)`
- [ ] `RadioButtonRow(selected, onClick, label)` — radio + label en row

### 3.5 ComboBox amélioré
- [ ] `EditableComboBox(value, onValueChange, options)` — saisie libre + suggestions
- [ ] `ListComboBox(selectedIndex, onSelect, items)` — sélection dans liste
- [ ] SpeedSearch intégré dans les options

### 3.6 SpeedSearch
- [ ] `SpeedSearchArea(state, content)` — zone avec recherche type-ahead
- [ ] `SpeedSearchState` — gestion de la recherche
- [ ] Intégrer dans LazyTree, SelectableLazyColumn, ComboBox, Sidebar
- [ ] Highlighting des matches

### 3.7 GroupHeader
- [ ] `GroupHeader(text, modifier)` — en-tête de section pour listes
- [ ] `GroupHeaderStyle(colors, metrics)`

### 3.8 Composants macOS natifs
- [ ] `Stepper(value, onValueChange, range)` — NSStepper (+/-)
- [ ] `DatePicker(date, onDateChange)` — NSDatePicker
- [ ] `Sheet(visible, onDismiss, content)` — modal sheet macOS (glisse du haut)
- [ ] `PathBar(segments, onSelect)` — NSPathControl breadcrumb
- [ ] `TokenField(tokens, onTokensChange)` — NSTokenField
- [ ] `LevelIndicator(value, type)` — rating, relevance, capacity

---

## Phase 4 — Améliorations composants existants

### 4.1 Tabs
- [ ] Close button sur les tabs
- [ ] Drag-to-reorder
- [ ] Tab icons
- [ ] Overflow menu quand trop de tabs

### 4.2 ContextMenu
- [ ] Support sous-menus (`ContextSubmenu`)
- [ ] Raccourcis clavier affichés dans les items
- [ ] Icônes dans les items

### 4.3 Sidebar
- [ ] SpeedSearch intégré pour filtrage
- [ ] Drag-and-drop pour réordonner
- [ ] Badges améliorés (couleurs, types)

### 4.4 Tooltip
- [ ] `TooltipArea(tooltip, content)` — zone de déclenchement
- [ ] Positionnement intelligent (évite les bords d'écran)
- [ ] Délai configurable

### 4.5 Popover
- [ ] Arrow/caret pointant vers l'ancre
- [ ] Animation macOS native (scale + fade)
- [ ] Détection bords d'écran

---

## Phase 5 — Utilitaires et polish

### 5.1 Modifiers
- [ ] `Modifier.border(stroke)` avec Stroke(color, width, alignment: Inside/Center/Outside)
- [ ] `Modifier.onHover(callback)` — callback hover simplifié
- [ ] `Modifier.disabledAppearance()` — apparence désactivée macOS (opacity + saturation)

### 5.2 Système d'icônes
- [ ] Augmenter la couverture (100+ icônes SF Symbols-inspired)
- [ ] Variantes : filled, outlined, badge overlay
- [ ] `PainterProvider` interface avec cache
- [ ] Hint system : Dark, Selected, Size, Stroke

### 5.3 ComponentStyling centralisé
- [ ] `ComponentStyling` interface — registry de tous les styles
- [ ] `DefaultComponentStyling` — implémentation par défaut
- [ ] Permet de themer tous les composants en un seul endroit
- [ ] Intégrer dans `DarwinTheme`

### 5.4 Animations
- [ ] Animations de fenêtre inactive (griser les contrôles)
- [ ] Transition entre thèmes (light ↔ dark) animée
- [ ] Haptic feedback hooks pour iOS

---

## Résumé

| Phase | Contenu | Estimation |
|---|---|---|
| **Phase 1** | Fondations (Style, States, GlobalColors, Outline, Typography) | Structurant |
| **Phase 2** | Composants haute priorité (Tree, Split, Scrollbar, Link, Boutons, List) | Majeur |
| **Phase 3** | Composants moyenne priorité (Chips, Banners, SpeedSearch, macOS natifs) | Enrichissement |
| **Phase 4** | Amélioration composants existants (Tabs, Menu, Sidebar, Tooltip) | Raffinement |
| **Phase 5** | Utilitaires et polish (Modifiers, Icônes, ComponentStyling) | Finition |
