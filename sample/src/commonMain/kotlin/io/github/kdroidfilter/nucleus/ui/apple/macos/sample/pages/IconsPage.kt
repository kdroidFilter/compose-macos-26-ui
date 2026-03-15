package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SearchField
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icons
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.SystemIcon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.IconsExtended
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Accessibility
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Activity
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.AlarmClock
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Archive
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ArrowDown
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ArrowDownLeft
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ArrowDownRight
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ArrowLeft
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ArrowRight
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ArrowUp
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ArrowUpDown
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ArrowUpLeft
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ArrowUpRight
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.AtSign
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Ban
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Barcode
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Battery
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.BatteryCharging
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.BatteryFull
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.BatteryLow
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Bell
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.BellOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.BellRing
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Bold
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Book
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.BookOpen
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Bookmark
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Bug
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CalendarCheck
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CalendarMinus
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CalendarPlus
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Camera
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Car
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ChartBar
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ChartLine
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ChartPie
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ChevronUp
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ChevronsDown
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ChevronsRight
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ChevronsUp
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Circle
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CircleAlert
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CircleArrowDown
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CircleArrowLeft
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CircleArrowRight
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CircleArrowUp
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CircleDot
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CircleMinus
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CirclePlus
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Clipboard
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Clock
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Cloud
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CloudDownload
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CloudLightning
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CloudOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CloudRain
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CloudSnow
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CloudUpload
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Code
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Columns2
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Columns3
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Compass
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Cpu
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.CreditCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Crosshair
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Crown
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Droplet
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Dumbbell
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.EllipsisVertical
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Eraser
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ExternalLink
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Eye
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.EyeOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.File
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.FilePlus
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.FileText
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Flag
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.FlagOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Flame
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.FolderMinus
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.FolderOpen
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.FolderPlus
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Gauge
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Gift
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Globe
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.GraduationCap
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Grid2x2
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Grid3x3
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Hammer
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Hand
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.HardDrive
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Hash
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Headphones
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.HeartOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Hourglass
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Infinity
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Italic
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Key
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.KeyRound
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Keyboard
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Laptop
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Layers
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Leaf
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Lightbulb
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.LightbulbOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Link
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ListCheck
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ListOrdered
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Locate
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Lock
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.LockOpen
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.LogIn
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Mail
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.MailOpen
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Map
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.MapPin
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Maximize
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Menu
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.MessageCircle
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.MessageSquare
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Mic
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.MicOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Minimize
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Minus
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Monitor
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Music
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Navigation2
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Newspaper
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Paintbrush
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Palette
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.PanelRight
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Pause
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Percent
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Phone
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.PhoneCall
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.PhoneIncoming
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.PhoneOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.PhoneOutgoing
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Pill
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Pin
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.PinOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Pipette
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Plane
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Play
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Power
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Printer
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.QrCode
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Redo
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.RefreshCcw
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.RefreshCw
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Repeat
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Repeat1
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Rocket
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Rows2
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Ruler
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Save
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Scale
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Scan
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Scissors
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Send
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Share
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Shield
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ShieldAlert
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ShieldCheck
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ShieldOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ShoppingBag
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ShoppingCart
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Shuffle
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.SkipBack
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.SkipForward
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.SlidersHorizontal
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Smartphone
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Snowflake
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Sparkles
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Square
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.StarHalf
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Stethoscope
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Strikethrough
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Sunrise
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Sunset
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Syringe
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Table
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Tablet
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Target
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Terminal
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ThumbsDown
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ThumbsUp
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Timer
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Trash
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.TrendingDown
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.TrendingUp
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Triangle
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Trophy
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Tv
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Type
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Underline
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Undo
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.User
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.UserMinus
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.UserPlus
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.UserRound
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Users
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Video
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.VideoOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Volume
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Volume1
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Volume2
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.VolumeOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.VolumeX
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.WandSparkles
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Wifi
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.WifiOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Wind
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Wrench
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.Zap
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ZapOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ZoomIn
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.extended.ZoomOut
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

private data class IconEntry(val name: String, val icon: SystemIcon)

private val coreIcons = listOf(
    IconEntry("Plus", Icons.Plus),
    IconEntry("Settings", Icons.Settings),
    IconEntry("Heart", Icons.Heart),
    IconEntry("X", Icons.X),
    IconEntry("Check", Icons.Check),
    IconEntry("Trash2", Icons.Trash2),
    IconEntry("Download", Icons.Download),
    IconEntry("Share2", Icons.Share2),
    IconEntry("Sun", Icons.Sun),
    IconEntry("Moon", Icons.Moon),
    IconEntry("Search", Icons.Search),
    IconEntry("ChevronDown", Icons.ChevronDown),
    IconEntry("Info", Icons.Info),
    IconEntry("TriangleAlert", Icons.TriangleAlert),
    IconEntry("CircleCheck", Icons.CircleCheck),
    IconEntry("CircleX", Icons.CircleX),
    IconEntry("Calendar", Icons.Calendar),
    IconEntry("ChevronLeft", Icons.ChevronLeft),
    IconEntry("Copy", Icons.Copy),
    IconEntry("Upload", Icons.Upload),
    IconEntry("ImageIcon", Icons.ImageIcon),
    IconEntry("Star", Icons.Star),
    IconEntry("StarOff", Icons.StarOff),
    IconEntry("ArrowLeftRight", Icons.ArrowLeftRight),
    IconEntry("ChevronRight", Icons.ChevronRight),
    IconEntry("ChevronsLeft", Icons.ChevronsLeft),
    IconEntry("LogOut", Icons.LogOut),
    IconEntry("Home", Icons.Home),
    IconEntry("Folder", Icons.Folder),
    IconEntry("BarChart3", Icons.BarChart3),
    IconEntry("ChevronsUpDown", Icons.ChevronsUpDown),
    IconEntry("PanelLeft", Icons.PanelLeft),
    IconEntry("LayoutGrid", Icons.LayoutGrid),
    IconEntry("List", Icons.List),
    IconEntry("Ellipsis", Icons.Ellipsis),
    IconEntry("Tag", Icons.Tag),
)

private val extendedIcons = listOf(
    // Navigation
    IconEntry("ArrowUp", IconsExtended.ArrowUp),
    IconEntry("ArrowDown", IconsExtended.ArrowDown),
    IconEntry("ArrowLeft", IconsExtended.ArrowLeft),
    IconEntry("ArrowRight", IconsExtended.ArrowRight),
    IconEntry("ArrowUpLeft", IconsExtended.ArrowUpLeft),
    IconEntry("ArrowUpRight", IconsExtended.ArrowUpRight),
    IconEntry("ArrowDownLeft", IconsExtended.ArrowDownLeft),
    IconEntry("ArrowDownRight", IconsExtended.ArrowDownRight),
    IconEntry("ArrowUpDown", IconsExtended.ArrowUpDown),
    IconEntry("ChevronUp", IconsExtended.ChevronUp),
    IconEntry("ChevronsUp", IconsExtended.ChevronsUp),
    IconEntry("ChevronsDown", IconsExtended.ChevronsDown),
    IconEntry("ChevronsRight", IconsExtended.ChevronsRight),
    // Actions
    IconEntry("Minus", IconsExtended.Minus),
    IconEntry("ZoomIn", IconsExtended.ZoomIn),
    IconEntry("ZoomOut", IconsExtended.ZoomOut),
    IconEntry("Trash", IconsExtended.Trash),
    IconEntry("Share", IconsExtended.Share),
    IconEntry("Clipboard", IconsExtended.Clipboard),
    IconEntry("Scissors", IconsExtended.Scissors),
    IconEntry("RefreshCw", IconsExtended.RefreshCw),
    IconEntry("RefreshCcw", IconsExtended.RefreshCcw),
    IconEntry("Undo", IconsExtended.Undo),
    IconEntry("Redo", IconsExtended.Redo),
    IconEntry("Save", IconsExtended.Save),
    IconEntry("ExternalLink", IconsExtended.ExternalLink),
    IconEntry("Link", IconsExtended.Link),
    IconEntry("LogIn", IconsExtended.LogIn),
    // Status
    IconEntry("HeartOff", IconsExtended.HeartOff),
    IconEntry("StarHalf", IconsExtended.StarHalf),
    IconEntry("Eye", IconsExtended.Eye),
    IconEntry("EyeOff", IconsExtended.EyeOff),
    IconEntry("Bell", IconsExtended.Bell),
    IconEntry("BellOff", IconsExtended.BellOff),
    IconEntry("BellRing", IconsExtended.BellRing),
    IconEntry("Bookmark", IconsExtended.Bookmark),
    IconEntry("Flag", IconsExtended.Flag),
    IconEntry("FlagOff", IconsExtended.FlagOff),
    IconEntry("Ban", IconsExtended.Ban),
    // Weather
    IconEntry("Cloud", IconsExtended.Cloud),
    IconEntry("CloudRain", IconsExtended.CloudRain),
    IconEntry("CloudSnow", IconsExtended.CloudSnow),
    IconEntry("CloudLightning", IconsExtended.CloudLightning),
    IconEntry("CloudOff", IconsExtended.CloudOff),
    IconEntry("CloudDownload", IconsExtended.CloudDownload),
    IconEntry("CloudUpload", IconsExtended.CloudUpload),
    IconEntry("Snowflake", IconsExtended.Snowflake),
    IconEntry("Wind", IconsExtended.Wind),
    IconEntry("Sunrise", IconsExtended.Sunrise),
    IconEntry("Sunset", IconsExtended.Sunset),
    IconEntry("Droplet", IconsExtended.Droplet),
    // Calendar & Time
    IconEntry("CalendarPlus", IconsExtended.CalendarPlus),
    IconEntry("CalendarMinus", IconsExtended.CalendarMinus),
    IconEntry("CalendarCheck", IconsExtended.CalendarCheck),
    IconEntry("Clock", IconsExtended.Clock),
    IconEntry("Timer", IconsExtended.Timer),
    IconEntry("Hourglass", IconsExtended.Hourglass),
    IconEntry("AlarmClock", IconsExtended.AlarmClock),
    // Communication
    IconEntry("Mail", IconsExtended.Mail),
    IconEntry("MailOpen", IconsExtended.MailOpen),
    IconEntry("Phone", IconsExtended.Phone),
    IconEntry("PhoneCall", IconsExtended.PhoneCall),
    IconEntry("PhoneOff", IconsExtended.PhoneOff),
    IconEntry("PhoneIncoming", IconsExtended.PhoneIncoming),
    IconEntry("PhoneOutgoing", IconsExtended.PhoneOutgoing),
    IconEntry("MessageCircle", IconsExtended.MessageCircle),
    IconEntry("MessageSquare", IconsExtended.MessageSquare),
    IconEntry("Send", IconsExtended.Send),
    // Media
    IconEntry("Play", IconsExtended.Play),
    IconEntry("Pause", IconsExtended.Pause),
    IconEntry("SkipForward", IconsExtended.SkipForward),
    IconEntry("SkipBack", IconsExtended.SkipBack),
    IconEntry("Volume", IconsExtended.Volume),
    IconEntry("Volume1", IconsExtended.Volume1),
    IconEntry("Volume2", IconsExtended.Volume2),
    IconEntry("VolumeX", IconsExtended.VolumeX),
    IconEntry("VolumeOff", IconsExtended.VolumeOff),
    IconEntry("Mic", IconsExtended.Mic),
    IconEntry("MicOff", IconsExtended.MicOff),
    IconEntry("Camera", IconsExtended.Camera),
    IconEntry("Video", IconsExtended.Video),
    IconEntry("VideoOff", IconsExtended.VideoOff),
    IconEntry("Music", IconsExtended.Music),
    IconEntry("Headphones", IconsExtended.Headphones),
    // Files & Folders
    IconEntry("File", IconsExtended.File),
    IconEntry("FileText", IconsExtended.FileText),
    IconEntry("FilePlus", IconsExtended.FilePlus),
    IconEntry("FolderOpen", IconsExtended.FolderOpen),
    IconEntry("FolderPlus", IconsExtended.FolderPlus),
    IconEntry("FolderMinus", IconsExtended.FolderMinus),
    IconEntry("Archive", IconsExtended.Archive),
    // Devices
    IconEntry("Monitor", IconsExtended.Monitor),
    IconEntry("Laptop", IconsExtended.Laptop),
    IconEntry("Smartphone", IconsExtended.Smartphone),
    IconEntry("Tablet", IconsExtended.Tablet),
    IconEntry("Keyboard", IconsExtended.Keyboard),
    IconEntry("Printer", IconsExtended.Printer),
    IconEntry("HardDrive", IconsExtended.HardDrive),
    IconEntry("Cpu", IconsExtended.Cpu),
    IconEntry("Wifi", IconsExtended.Wifi),
    IconEntry("WifiOff", IconsExtended.WifiOff),
    IconEntry("Battery", IconsExtended.Battery),
    IconEntry("BatteryCharging", IconsExtended.BatteryCharging),
    IconEntry("BatteryFull", IconsExtended.BatteryFull),
    IconEntry("BatteryLow", IconsExtended.BatteryLow),
    IconEntry("Tv", IconsExtended.Tv),
    // Shapes & Status
    IconEntry("Circle", IconsExtended.Circle),
    IconEntry("CircleAlert", IconsExtended.CircleAlert),
    IconEntry("CirclePlus", IconsExtended.CirclePlus),
    IconEntry("CircleMinus", IconsExtended.CircleMinus),
    IconEntry("CircleDot", IconsExtended.CircleDot),
    IconEntry("CircleArrowUp", IconsExtended.CircleArrowUp),
    IconEntry("CircleArrowDown", IconsExtended.CircleArrowDown),
    IconEntry("CircleArrowLeft", IconsExtended.CircleArrowLeft),
    IconEntry("CircleArrowRight", IconsExtended.CircleArrowRight),
    IconEntry("Square", IconsExtended.Square),
    IconEntry("Triangle", IconsExtended.Triangle),
    // Security
    IconEntry("Shield", IconsExtended.Shield),
    IconEntry("ShieldCheck", IconsExtended.ShieldCheck),
    IconEntry("ShieldAlert", IconsExtended.ShieldAlert),
    IconEntry("ShieldOff", IconsExtended.ShieldOff),
    IconEntry("Lock", IconsExtended.Lock),
    IconEntry("LockOpen", IconsExtended.LockOpen),
    IconEntry("Key", IconsExtended.Key),
    IconEntry("KeyRound", IconsExtended.KeyRound),
    // Layout
    IconEntry("PanelRight", IconsExtended.PanelRight),
    IconEntry("Grid2x2", IconsExtended.Grid2x2),
    IconEntry("Grid3x3", IconsExtended.Grid3x3),
    IconEntry("ListOrdered", IconsExtended.ListOrdered),
    IconEntry("ListCheck", IconsExtended.ListCheck),
    IconEntry("Menu", IconsExtended.Menu),
    IconEntry("EllipsisVertical", IconsExtended.EllipsisVertical),
    IconEntry("Columns2", IconsExtended.Columns2),
    IconEntry("Columns3", IconsExtended.Columns3),
    IconEntry("Rows2", IconsExtended.Rows2),
    IconEntry("Table", IconsExtended.Table),
    IconEntry("SlidersHorizontal", IconsExtended.SlidersHorizontal),
    IconEntry("Layers", IconsExtended.Layers),
    // Charts
    IconEntry("ChartBar", IconsExtended.ChartBar),
    IconEntry("ChartLine", IconsExtended.ChartLine),
    IconEntry("ChartPie", IconsExtended.ChartPie),
    IconEntry("TrendingUp", IconsExtended.TrendingUp),
    IconEntry("TrendingDown", IconsExtended.TrendingDown),
    IconEntry("Activity", IconsExtended.Activity),
    IconEntry("Gauge", IconsExtended.Gauge),
    // People
    IconEntry("User", IconsExtended.User),
    IconEntry("UserPlus", IconsExtended.UserPlus),
    IconEntry("UserMinus", IconsExtended.UserMinus),
    IconEntry("Users", IconsExtended.Users),
    IconEntry("UserRound", IconsExtended.UserRound),
    IconEntry("Accessibility", IconsExtended.Accessibility),
    // Commerce
    IconEntry("ShoppingCart", IconsExtended.ShoppingCart),
    IconEntry("ShoppingBag", IconsExtended.ShoppingBag),
    IconEntry("CreditCard", IconsExtended.CreditCard),
    IconEntry("Gift", IconsExtended.Gift),
    IconEntry("Percent", IconsExtended.Percent),
    // Transport & Location
    IconEntry("Car", IconsExtended.Car),
    IconEntry("Plane", IconsExtended.Plane),
    IconEntry("Rocket", IconsExtended.Rocket),
    IconEntry("Map", IconsExtended.Map),
    IconEntry("MapPin", IconsExtended.MapPin),
    IconEntry("Globe", IconsExtended.Globe),
    IconEntry("Compass", IconsExtended.Compass),
    IconEntry("Navigation2", IconsExtended.Navigation2),
    IconEntry("Locate", IconsExtended.Locate),
    // Tools
    IconEntry("Wrench", IconsExtended.Wrench),
    IconEntry("Hammer", IconsExtended.Hammer),
    IconEntry("Paintbrush", IconsExtended.Paintbrush),
    IconEntry("Eraser", IconsExtended.Eraser),
    IconEntry("Ruler", IconsExtended.Ruler),
    IconEntry("Pipette", IconsExtended.Pipette),
    IconEntry("Palette", IconsExtended.Palette),
    IconEntry("Scan", IconsExtended.Scan),
    IconEntry("QrCode", IconsExtended.QrCode),
    IconEntry("Barcode", IconsExtended.Barcode),
    IconEntry("Pin", IconsExtended.Pin),
    IconEntry("PinOff", IconsExtended.PinOff),
    // Energy & Nature
    IconEntry("Lightbulb", IconsExtended.Lightbulb),
    IconEntry("LightbulbOff", IconsExtended.LightbulbOff),
    IconEntry("Zap", IconsExtended.Zap),
    IconEntry("ZapOff", IconsExtended.ZapOff),
    IconEntry("Power", IconsExtended.Power),
    IconEntry("Flame", IconsExtended.Flame),
    IconEntry("Leaf", IconsExtended.Leaf),
    // Text Formatting
    IconEntry("Bold", IconsExtended.Bold),
    IconEntry("Italic", IconsExtended.Italic),
    IconEntry("Underline", IconsExtended.Underline),
    IconEntry("Strikethrough", IconsExtended.Strikethrough),
    IconEntry("Type", IconsExtended.Type),
    IconEntry("Code", IconsExtended.Code),
    IconEntry("Hash", IconsExtended.Hash),
    IconEntry("AtSign", IconsExtended.AtSign),
    // Knowledge
    IconEntry("Book", IconsExtended.Book),
    IconEntry("BookOpen", IconsExtended.BookOpen),
    IconEntry("GraduationCap", IconsExtended.GraduationCap),
    IconEntry("Newspaper", IconsExtended.Newspaper),
    // Health & Fitness
    IconEntry("Stethoscope", IconsExtended.Stethoscope),
    IconEntry("Pill", IconsExtended.Pill),
    IconEntry("Syringe", IconsExtended.Syringe),
    IconEntry("Dumbbell", IconsExtended.Dumbbell),
    // Misc
    IconEntry("Bug", IconsExtended.Bug),
    IconEntry("Terminal", IconsExtended.Terminal),
    IconEntry("Target", IconsExtended.Target),
    IconEntry("Crosshair", IconsExtended.Crosshair),
    IconEntry("Hand", IconsExtended.Hand),
    IconEntry("ThumbsUp", IconsExtended.ThumbsUp),
    IconEntry("ThumbsDown", IconsExtended.ThumbsDown),
    IconEntry("Sparkles", IconsExtended.Sparkles),
    IconEntry("WandSparkles", IconsExtended.WandSparkles),
    IconEntry("Trophy", IconsExtended.Trophy),
    IconEntry("Crown", IconsExtended.Crown),
    IconEntry("Scale", IconsExtended.Scale),
    IconEntry("Infinity", IconsExtended.Infinity),
    IconEntry("Repeat", IconsExtended.Repeat),
    IconEntry("Repeat1", IconsExtended.Repeat1),
    IconEntry("Shuffle", IconsExtended.Shuffle),
    IconEntry("Maximize", IconsExtended.Maximize),
    IconEntry("Minimize", IconsExtended.Minimize),
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun IconGrid(icons: List<IconEntry>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        icons.forEach { entry ->
            Column(
                modifier = Modifier.width(80.dp).padding(vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    icon = entry.icon,
                    contentDescription = entry.name,
                    modifier = Modifier.size(24.dp),
                )
                Text(
                    text = entry.name,
                    style = MacosTheme.typography.caption2,
                    color = MacosTheme.colorScheme.textSecondary,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
internal fun IconsPage() {
    var searchQuery by remember { mutableStateOf("") }
    val allIcons = remember { coreIcons + extendedIcons }
    val filteredCore = remember(searchQuery) {
        if (searchQuery.isBlank()) coreIcons
        else coreIcons.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }
    val filteredExtended = remember(searchQuery) {
        if (searchQuery.isBlank()) extendedIcons
        else extendedIcons.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }
    val totalCount = allIcons.size
    val matchCount = filteredCore.size + filteredExtended.size

    GalleryPage("Icons", "All available macOS icons with SF Symbol mapping.") {
        Text(
            text = "On Apple platforms (macOS and iOS), icons are rendered using native SF Symbols. " +
                "On all other platforms (Android, Web, Windows, Linux), Lucide vector icons are used as fallback.",
            style = MacosTheme.typography.callout,
            color = MacosTheme.colorScheme.textSecondary,
        )

        SearchField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = "Search icons ($totalCount available)...",
            modifier = Modifier.fillMaxWidth(),
        )

        if (filteredCore.isNotEmpty()) {
            SectionHeader("Core Icons (${filteredCore.size})")
            IconGrid(filteredCore)
        }

        if (filteredExtended.isNotEmpty()) {
            SectionHeader("Extended Icons (${filteredExtended.size})")
            IconGrid(filteredExtended)
        }

        if (matchCount == 0) {
            Text(
                text = "No icons matching \"$searchQuery\"",
                style = MacosTheme.typography.body,
                color = MacosTheme.colorScheme.textTertiary,
            )
        }
    }
}
