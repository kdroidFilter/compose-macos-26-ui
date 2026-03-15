package io.github.kdroidfilter.darwinui.sample.pages

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
import io.github.kdroidfilter.darwinui.components.SearchField
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.DarwinIcons
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.SystemIcon
import io.github.kdroidfilter.darwinui.icons.extended.DarwinIconsExtended
import io.github.kdroidfilter.darwinui.icons.extended.Accessibility
import io.github.kdroidfilter.darwinui.icons.extended.Activity
import io.github.kdroidfilter.darwinui.icons.extended.AlarmClock
import io.github.kdroidfilter.darwinui.icons.extended.Archive
import io.github.kdroidfilter.darwinui.icons.extended.ArrowDown
import io.github.kdroidfilter.darwinui.icons.extended.ArrowDownLeft
import io.github.kdroidfilter.darwinui.icons.extended.ArrowDownRight
import io.github.kdroidfilter.darwinui.icons.extended.ArrowLeft
import io.github.kdroidfilter.darwinui.icons.extended.ArrowRight
import io.github.kdroidfilter.darwinui.icons.extended.ArrowUp
import io.github.kdroidfilter.darwinui.icons.extended.ArrowUpDown
import io.github.kdroidfilter.darwinui.icons.extended.ArrowUpLeft
import io.github.kdroidfilter.darwinui.icons.extended.ArrowUpRight
import io.github.kdroidfilter.darwinui.icons.extended.AtSign
import io.github.kdroidfilter.darwinui.icons.extended.Ban
import io.github.kdroidfilter.darwinui.icons.extended.Barcode
import io.github.kdroidfilter.darwinui.icons.extended.Battery
import io.github.kdroidfilter.darwinui.icons.extended.BatteryCharging
import io.github.kdroidfilter.darwinui.icons.extended.BatteryFull
import io.github.kdroidfilter.darwinui.icons.extended.BatteryLow
import io.github.kdroidfilter.darwinui.icons.extended.Bell
import io.github.kdroidfilter.darwinui.icons.extended.BellOff
import io.github.kdroidfilter.darwinui.icons.extended.BellRing
import io.github.kdroidfilter.darwinui.icons.extended.Bold
import io.github.kdroidfilter.darwinui.icons.extended.Book
import io.github.kdroidfilter.darwinui.icons.extended.BookOpen
import io.github.kdroidfilter.darwinui.icons.extended.Bookmark
import io.github.kdroidfilter.darwinui.icons.extended.Bug
import io.github.kdroidfilter.darwinui.icons.extended.CalendarCheck
import io.github.kdroidfilter.darwinui.icons.extended.CalendarMinus
import io.github.kdroidfilter.darwinui.icons.extended.CalendarPlus
import io.github.kdroidfilter.darwinui.icons.extended.Camera
import io.github.kdroidfilter.darwinui.icons.extended.Car
import io.github.kdroidfilter.darwinui.icons.extended.ChartBar
import io.github.kdroidfilter.darwinui.icons.extended.ChartLine
import io.github.kdroidfilter.darwinui.icons.extended.ChartPie
import io.github.kdroidfilter.darwinui.icons.extended.ChevronUp
import io.github.kdroidfilter.darwinui.icons.extended.ChevronsDown
import io.github.kdroidfilter.darwinui.icons.extended.ChevronsRight
import io.github.kdroidfilter.darwinui.icons.extended.ChevronsUp
import io.github.kdroidfilter.darwinui.icons.extended.Circle
import io.github.kdroidfilter.darwinui.icons.extended.CircleAlert
import io.github.kdroidfilter.darwinui.icons.extended.CircleArrowDown
import io.github.kdroidfilter.darwinui.icons.extended.CircleArrowLeft
import io.github.kdroidfilter.darwinui.icons.extended.CircleArrowRight
import io.github.kdroidfilter.darwinui.icons.extended.CircleArrowUp
import io.github.kdroidfilter.darwinui.icons.extended.CircleDot
import io.github.kdroidfilter.darwinui.icons.extended.CircleMinus
import io.github.kdroidfilter.darwinui.icons.extended.CirclePlus
import io.github.kdroidfilter.darwinui.icons.extended.Clipboard
import io.github.kdroidfilter.darwinui.icons.extended.Clock
import io.github.kdroidfilter.darwinui.icons.extended.Cloud
import io.github.kdroidfilter.darwinui.icons.extended.CloudDownload
import io.github.kdroidfilter.darwinui.icons.extended.CloudLightning
import io.github.kdroidfilter.darwinui.icons.extended.CloudOff
import io.github.kdroidfilter.darwinui.icons.extended.CloudRain
import io.github.kdroidfilter.darwinui.icons.extended.CloudSnow
import io.github.kdroidfilter.darwinui.icons.extended.CloudUpload
import io.github.kdroidfilter.darwinui.icons.extended.Code
import io.github.kdroidfilter.darwinui.icons.extended.Columns2
import io.github.kdroidfilter.darwinui.icons.extended.Columns3
import io.github.kdroidfilter.darwinui.icons.extended.Compass
import io.github.kdroidfilter.darwinui.icons.extended.Cpu
import io.github.kdroidfilter.darwinui.icons.extended.CreditCard
import io.github.kdroidfilter.darwinui.icons.extended.Crosshair
import io.github.kdroidfilter.darwinui.icons.extended.Crown
import io.github.kdroidfilter.darwinui.icons.extended.Droplet
import io.github.kdroidfilter.darwinui.icons.extended.Dumbbell
import io.github.kdroidfilter.darwinui.icons.extended.EllipsisVertical
import io.github.kdroidfilter.darwinui.icons.extended.Eraser
import io.github.kdroidfilter.darwinui.icons.extended.ExternalLink
import io.github.kdroidfilter.darwinui.icons.extended.Eye
import io.github.kdroidfilter.darwinui.icons.extended.EyeOff
import io.github.kdroidfilter.darwinui.icons.extended.File
import io.github.kdroidfilter.darwinui.icons.extended.FilePlus
import io.github.kdroidfilter.darwinui.icons.extended.FileText
import io.github.kdroidfilter.darwinui.icons.extended.Flag
import io.github.kdroidfilter.darwinui.icons.extended.FlagOff
import io.github.kdroidfilter.darwinui.icons.extended.Flame
import io.github.kdroidfilter.darwinui.icons.extended.FolderMinus
import io.github.kdroidfilter.darwinui.icons.extended.FolderOpen
import io.github.kdroidfilter.darwinui.icons.extended.FolderPlus
import io.github.kdroidfilter.darwinui.icons.extended.Gauge
import io.github.kdroidfilter.darwinui.icons.extended.Gift
import io.github.kdroidfilter.darwinui.icons.extended.Globe
import io.github.kdroidfilter.darwinui.icons.extended.GraduationCap
import io.github.kdroidfilter.darwinui.icons.extended.Grid2x2
import io.github.kdroidfilter.darwinui.icons.extended.Grid3x3
import io.github.kdroidfilter.darwinui.icons.extended.Hammer
import io.github.kdroidfilter.darwinui.icons.extended.Hand
import io.github.kdroidfilter.darwinui.icons.extended.HardDrive
import io.github.kdroidfilter.darwinui.icons.extended.Hash
import io.github.kdroidfilter.darwinui.icons.extended.Headphones
import io.github.kdroidfilter.darwinui.icons.extended.HeartOff
import io.github.kdroidfilter.darwinui.icons.extended.Hourglass
import io.github.kdroidfilter.darwinui.icons.extended.Infinity
import io.github.kdroidfilter.darwinui.icons.extended.Italic
import io.github.kdroidfilter.darwinui.icons.extended.Key
import io.github.kdroidfilter.darwinui.icons.extended.KeyRound
import io.github.kdroidfilter.darwinui.icons.extended.Keyboard
import io.github.kdroidfilter.darwinui.icons.extended.Laptop
import io.github.kdroidfilter.darwinui.icons.extended.Layers
import io.github.kdroidfilter.darwinui.icons.extended.Leaf
import io.github.kdroidfilter.darwinui.icons.extended.Lightbulb
import io.github.kdroidfilter.darwinui.icons.extended.LightbulbOff
import io.github.kdroidfilter.darwinui.icons.extended.Link
import io.github.kdroidfilter.darwinui.icons.extended.ListCheck
import io.github.kdroidfilter.darwinui.icons.extended.ListOrdered
import io.github.kdroidfilter.darwinui.icons.extended.Locate
import io.github.kdroidfilter.darwinui.icons.extended.Lock
import io.github.kdroidfilter.darwinui.icons.extended.LockOpen
import io.github.kdroidfilter.darwinui.icons.extended.LogIn
import io.github.kdroidfilter.darwinui.icons.extended.Mail
import io.github.kdroidfilter.darwinui.icons.extended.MailOpen
import io.github.kdroidfilter.darwinui.icons.extended.Map
import io.github.kdroidfilter.darwinui.icons.extended.MapPin
import io.github.kdroidfilter.darwinui.icons.extended.Maximize
import io.github.kdroidfilter.darwinui.icons.extended.Menu
import io.github.kdroidfilter.darwinui.icons.extended.MessageCircle
import io.github.kdroidfilter.darwinui.icons.extended.MessageSquare
import io.github.kdroidfilter.darwinui.icons.extended.Mic
import io.github.kdroidfilter.darwinui.icons.extended.MicOff
import io.github.kdroidfilter.darwinui.icons.extended.Minimize
import io.github.kdroidfilter.darwinui.icons.extended.Minus
import io.github.kdroidfilter.darwinui.icons.extended.Monitor
import io.github.kdroidfilter.darwinui.icons.extended.Music
import io.github.kdroidfilter.darwinui.icons.extended.Navigation2
import io.github.kdroidfilter.darwinui.icons.extended.Newspaper
import io.github.kdroidfilter.darwinui.icons.extended.Paintbrush
import io.github.kdroidfilter.darwinui.icons.extended.Palette
import io.github.kdroidfilter.darwinui.icons.extended.PanelRight
import io.github.kdroidfilter.darwinui.icons.extended.Pause
import io.github.kdroidfilter.darwinui.icons.extended.Percent
import io.github.kdroidfilter.darwinui.icons.extended.Phone
import io.github.kdroidfilter.darwinui.icons.extended.PhoneCall
import io.github.kdroidfilter.darwinui.icons.extended.PhoneIncoming
import io.github.kdroidfilter.darwinui.icons.extended.PhoneOff
import io.github.kdroidfilter.darwinui.icons.extended.PhoneOutgoing
import io.github.kdroidfilter.darwinui.icons.extended.Pill
import io.github.kdroidfilter.darwinui.icons.extended.Pin
import io.github.kdroidfilter.darwinui.icons.extended.PinOff
import io.github.kdroidfilter.darwinui.icons.extended.Pipette
import io.github.kdroidfilter.darwinui.icons.extended.Plane
import io.github.kdroidfilter.darwinui.icons.extended.Play
import io.github.kdroidfilter.darwinui.icons.extended.Power
import io.github.kdroidfilter.darwinui.icons.extended.Printer
import io.github.kdroidfilter.darwinui.icons.extended.QrCode
import io.github.kdroidfilter.darwinui.icons.extended.Redo
import io.github.kdroidfilter.darwinui.icons.extended.RefreshCcw
import io.github.kdroidfilter.darwinui.icons.extended.RefreshCw
import io.github.kdroidfilter.darwinui.icons.extended.Repeat
import io.github.kdroidfilter.darwinui.icons.extended.Repeat1
import io.github.kdroidfilter.darwinui.icons.extended.Rocket
import io.github.kdroidfilter.darwinui.icons.extended.Rows2
import io.github.kdroidfilter.darwinui.icons.extended.Ruler
import io.github.kdroidfilter.darwinui.icons.extended.Save
import io.github.kdroidfilter.darwinui.icons.extended.Scale
import io.github.kdroidfilter.darwinui.icons.extended.Scan
import io.github.kdroidfilter.darwinui.icons.extended.Scissors
import io.github.kdroidfilter.darwinui.icons.extended.Send
import io.github.kdroidfilter.darwinui.icons.extended.Share
import io.github.kdroidfilter.darwinui.icons.extended.Shield
import io.github.kdroidfilter.darwinui.icons.extended.ShieldAlert
import io.github.kdroidfilter.darwinui.icons.extended.ShieldCheck
import io.github.kdroidfilter.darwinui.icons.extended.ShieldOff
import io.github.kdroidfilter.darwinui.icons.extended.ShoppingBag
import io.github.kdroidfilter.darwinui.icons.extended.ShoppingCart
import io.github.kdroidfilter.darwinui.icons.extended.Shuffle
import io.github.kdroidfilter.darwinui.icons.extended.SkipBack
import io.github.kdroidfilter.darwinui.icons.extended.SkipForward
import io.github.kdroidfilter.darwinui.icons.extended.SlidersHorizontal
import io.github.kdroidfilter.darwinui.icons.extended.Smartphone
import io.github.kdroidfilter.darwinui.icons.extended.Snowflake
import io.github.kdroidfilter.darwinui.icons.extended.Sparkles
import io.github.kdroidfilter.darwinui.icons.extended.Square
import io.github.kdroidfilter.darwinui.icons.extended.StarHalf
import io.github.kdroidfilter.darwinui.icons.extended.Stethoscope
import io.github.kdroidfilter.darwinui.icons.extended.Strikethrough
import io.github.kdroidfilter.darwinui.icons.extended.Sunrise
import io.github.kdroidfilter.darwinui.icons.extended.Sunset
import io.github.kdroidfilter.darwinui.icons.extended.Syringe
import io.github.kdroidfilter.darwinui.icons.extended.Table
import io.github.kdroidfilter.darwinui.icons.extended.Tablet
import io.github.kdroidfilter.darwinui.icons.extended.Target
import io.github.kdroidfilter.darwinui.icons.extended.Terminal
import io.github.kdroidfilter.darwinui.icons.extended.ThumbsDown
import io.github.kdroidfilter.darwinui.icons.extended.ThumbsUp
import io.github.kdroidfilter.darwinui.icons.extended.Timer
import io.github.kdroidfilter.darwinui.icons.extended.Trash
import io.github.kdroidfilter.darwinui.icons.extended.TrendingDown
import io.github.kdroidfilter.darwinui.icons.extended.TrendingUp
import io.github.kdroidfilter.darwinui.icons.extended.Triangle
import io.github.kdroidfilter.darwinui.icons.extended.Trophy
import io.github.kdroidfilter.darwinui.icons.extended.Tv
import io.github.kdroidfilter.darwinui.icons.extended.Type
import io.github.kdroidfilter.darwinui.icons.extended.Underline
import io.github.kdroidfilter.darwinui.icons.extended.Undo
import io.github.kdroidfilter.darwinui.icons.extended.User
import io.github.kdroidfilter.darwinui.icons.extended.UserMinus
import io.github.kdroidfilter.darwinui.icons.extended.UserPlus
import io.github.kdroidfilter.darwinui.icons.extended.UserRound
import io.github.kdroidfilter.darwinui.icons.extended.Users
import io.github.kdroidfilter.darwinui.icons.extended.Video
import io.github.kdroidfilter.darwinui.icons.extended.VideoOff
import io.github.kdroidfilter.darwinui.icons.extended.Volume
import io.github.kdroidfilter.darwinui.icons.extended.Volume1
import io.github.kdroidfilter.darwinui.icons.extended.Volume2
import io.github.kdroidfilter.darwinui.icons.extended.VolumeOff
import io.github.kdroidfilter.darwinui.icons.extended.VolumeX
import io.github.kdroidfilter.darwinui.icons.extended.WandSparkles
import io.github.kdroidfilter.darwinui.icons.extended.Wifi
import io.github.kdroidfilter.darwinui.icons.extended.WifiOff
import io.github.kdroidfilter.darwinui.icons.extended.Wind
import io.github.kdroidfilter.darwinui.icons.extended.Wrench
import io.github.kdroidfilter.darwinui.icons.extended.Zap
import io.github.kdroidfilter.darwinui.icons.extended.ZapOff
import io.github.kdroidfilter.darwinui.icons.extended.ZoomIn
import io.github.kdroidfilter.darwinui.icons.extended.ZoomOut
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

private data class IconEntry(val name: String, val icon: SystemIcon)

private val coreIcons = listOf(
    IconEntry("Plus", DarwinIcons.Plus),
    IconEntry("Settings", DarwinIcons.Settings),
    IconEntry("Heart", DarwinIcons.Heart),
    IconEntry("X", DarwinIcons.X),
    IconEntry("Check", DarwinIcons.Check),
    IconEntry("Trash2", DarwinIcons.Trash2),
    IconEntry("Download", DarwinIcons.Download),
    IconEntry("Share2", DarwinIcons.Share2),
    IconEntry("Sun", DarwinIcons.Sun),
    IconEntry("Moon", DarwinIcons.Moon),
    IconEntry("Search", DarwinIcons.Search),
    IconEntry("ChevronDown", DarwinIcons.ChevronDown),
    IconEntry("Info", DarwinIcons.Info),
    IconEntry("TriangleAlert", DarwinIcons.TriangleAlert),
    IconEntry("CircleCheck", DarwinIcons.CircleCheck),
    IconEntry("CircleX", DarwinIcons.CircleX),
    IconEntry("Calendar", DarwinIcons.Calendar),
    IconEntry("ChevronLeft", DarwinIcons.ChevronLeft),
    IconEntry("Copy", DarwinIcons.Copy),
    IconEntry("Upload", DarwinIcons.Upload),
    IconEntry("ImageIcon", DarwinIcons.ImageIcon),
    IconEntry("Star", DarwinIcons.Star),
    IconEntry("StarOff", DarwinIcons.StarOff),
    IconEntry("ArrowLeftRight", DarwinIcons.ArrowLeftRight),
    IconEntry("ChevronRight", DarwinIcons.ChevronRight),
    IconEntry("ChevronsLeft", DarwinIcons.ChevronsLeft),
    IconEntry("LogOut", DarwinIcons.LogOut),
    IconEntry("Home", DarwinIcons.Home),
    IconEntry("Folder", DarwinIcons.Folder),
    IconEntry("BarChart3", DarwinIcons.BarChart3),
    IconEntry("ChevronsUpDown", DarwinIcons.ChevronsUpDown),
    IconEntry("PanelLeft", DarwinIcons.PanelLeft),
    IconEntry("LayoutGrid", DarwinIcons.LayoutGrid),
    IconEntry("List", DarwinIcons.List),
    IconEntry("Ellipsis", DarwinIcons.Ellipsis),
    IconEntry("Tag", DarwinIcons.Tag),
)

private val extendedIcons = listOf(
    // Navigation
    IconEntry("ArrowUp", DarwinIconsExtended.ArrowUp),
    IconEntry("ArrowDown", DarwinIconsExtended.ArrowDown),
    IconEntry("ArrowLeft", DarwinIconsExtended.ArrowLeft),
    IconEntry("ArrowRight", DarwinIconsExtended.ArrowRight),
    IconEntry("ArrowUpLeft", DarwinIconsExtended.ArrowUpLeft),
    IconEntry("ArrowUpRight", DarwinIconsExtended.ArrowUpRight),
    IconEntry("ArrowDownLeft", DarwinIconsExtended.ArrowDownLeft),
    IconEntry("ArrowDownRight", DarwinIconsExtended.ArrowDownRight),
    IconEntry("ArrowUpDown", DarwinIconsExtended.ArrowUpDown),
    IconEntry("ChevronUp", DarwinIconsExtended.ChevronUp),
    IconEntry("ChevronsUp", DarwinIconsExtended.ChevronsUp),
    IconEntry("ChevronsDown", DarwinIconsExtended.ChevronsDown),
    IconEntry("ChevronsRight", DarwinIconsExtended.ChevronsRight),
    // Actions
    IconEntry("Minus", DarwinIconsExtended.Minus),
    IconEntry("ZoomIn", DarwinIconsExtended.ZoomIn),
    IconEntry("ZoomOut", DarwinIconsExtended.ZoomOut),
    IconEntry("Trash", DarwinIconsExtended.Trash),
    IconEntry("Share", DarwinIconsExtended.Share),
    IconEntry("Clipboard", DarwinIconsExtended.Clipboard),
    IconEntry("Scissors", DarwinIconsExtended.Scissors),
    IconEntry("RefreshCw", DarwinIconsExtended.RefreshCw),
    IconEntry("RefreshCcw", DarwinIconsExtended.RefreshCcw),
    IconEntry("Undo", DarwinIconsExtended.Undo),
    IconEntry("Redo", DarwinIconsExtended.Redo),
    IconEntry("Save", DarwinIconsExtended.Save),
    IconEntry("ExternalLink", DarwinIconsExtended.ExternalLink),
    IconEntry("Link", DarwinIconsExtended.Link),
    IconEntry("LogIn", DarwinIconsExtended.LogIn),
    // Status
    IconEntry("HeartOff", DarwinIconsExtended.HeartOff),
    IconEntry("StarHalf", DarwinIconsExtended.StarHalf),
    IconEntry("Eye", DarwinIconsExtended.Eye),
    IconEntry("EyeOff", DarwinIconsExtended.EyeOff),
    IconEntry("Bell", DarwinIconsExtended.Bell),
    IconEntry("BellOff", DarwinIconsExtended.BellOff),
    IconEntry("BellRing", DarwinIconsExtended.BellRing),
    IconEntry("Bookmark", DarwinIconsExtended.Bookmark),
    IconEntry("Flag", DarwinIconsExtended.Flag),
    IconEntry("FlagOff", DarwinIconsExtended.FlagOff),
    IconEntry("Ban", DarwinIconsExtended.Ban),
    // Weather
    IconEntry("Cloud", DarwinIconsExtended.Cloud),
    IconEntry("CloudRain", DarwinIconsExtended.CloudRain),
    IconEntry("CloudSnow", DarwinIconsExtended.CloudSnow),
    IconEntry("CloudLightning", DarwinIconsExtended.CloudLightning),
    IconEntry("CloudOff", DarwinIconsExtended.CloudOff),
    IconEntry("CloudDownload", DarwinIconsExtended.CloudDownload),
    IconEntry("CloudUpload", DarwinIconsExtended.CloudUpload),
    IconEntry("Snowflake", DarwinIconsExtended.Snowflake),
    IconEntry("Wind", DarwinIconsExtended.Wind),
    IconEntry("Sunrise", DarwinIconsExtended.Sunrise),
    IconEntry("Sunset", DarwinIconsExtended.Sunset),
    IconEntry("Droplet", DarwinIconsExtended.Droplet),
    // Calendar & Time
    IconEntry("CalendarPlus", DarwinIconsExtended.CalendarPlus),
    IconEntry("CalendarMinus", DarwinIconsExtended.CalendarMinus),
    IconEntry("CalendarCheck", DarwinIconsExtended.CalendarCheck),
    IconEntry("Clock", DarwinIconsExtended.Clock),
    IconEntry("Timer", DarwinIconsExtended.Timer),
    IconEntry("Hourglass", DarwinIconsExtended.Hourglass),
    IconEntry("AlarmClock", DarwinIconsExtended.AlarmClock),
    // Communication
    IconEntry("Mail", DarwinIconsExtended.Mail),
    IconEntry("MailOpen", DarwinIconsExtended.MailOpen),
    IconEntry("Phone", DarwinIconsExtended.Phone),
    IconEntry("PhoneCall", DarwinIconsExtended.PhoneCall),
    IconEntry("PhoneOff", DarwinIconsExtended.PhoneOff),
    IconEntry("PhoneIncoming", DarwinIconsExtended.PhoneIncoming),
    IconEntry("PhoneOutgoing", DarwinIconsExtended.PhoneOutgoing),
    IconEntry("MessageCircle", DarwinIconsExtended.MessageCircle),
    IconEntry("MessageSquare", DarwinIconsExtended.MessageSquare),
    IconEntry("Send", DarwinIconsExtended.Send),
    // Media
    IconEntry("Play", DarwinIconsExtended.Play),
    IconEntry("Pause", DarwinIconsExtended.Pause),
    IconEntry("SkipForward", DarwinIconsExtended.SkipForward),
    IconEntry("SkipBack", DarwinIconsExtended.SkipBack),
    IconEntry("Volume", DarwinIconsExtended.Volume),
    IconEntry("Volume1", DarwinIconsExtended.Volume1),
    IconEntry("Volume2", DarwinIconsExtended.Volume2),
    IconEntry("VolumeX", DarwinIconsExtended.VolumeX),
    IconEntry("VolumeOff", DarwinIconsExtended.VolumeOff),
    IconEntry("Mic", DarwinIconsExtended.Mic),
    IconEntry("MicOff", DarwinIconsExtended.MicOff),
    IconEntry("Camera", DarwinIconsExtended.Camera),
    IconEntry("Video", DarwinIconsExtended.Video),
    IconEntry("VideoOff", DarwinIconsExtended.VideoOff),
    IconEntry("Music", DarwinIconsExtended.Music),
    IconEntry("Headphones", DarwinIconsExtended.Headphones),
    // Files & Folders
    IconEntry("File", DarwinIconsExtended.File),
    IconEntry("FileText", DarwinIconsExtended.FileText),
    IconEntry("FilePlus", DarwinIconsExtended.FilePlus),
    IconEntry("FolderOpen", DarwinIconsExtended.FolderOpen),
    IconEntry("FolderPlus", DarwinIconsExtended.FolderPlus),
    IconEntry("FolderMinus", DarwinIconsExtended.FolderMinus),
    IconEntry("Archive", DarwinIconsExtended.Archive),
    // Devices
    IconEntry("Monitor", DarwinIconsExtended.Monitor),
    IconEntry("Laptop", DarwinIconsExtended.Laptop),
    IconEntry("Smartphone", DarwinIconsExtended.Smartphone),
    IconEntry("Tablet", DarwinIconsExtended.Tablet),
    IconEntry("Keyboard", DarwinIconsExtended.Keyboard),
    IconEntry("Printer", DarwinIconsExtended.Printer),
    IconEntry("HardDrive", DarwinIconsExtended.HardDrive),
    IconEntry("Cpu", DarwinIconsExtended.Cpu),
    IconEntry("Wifi", DarwinIconsExtended.Wifi),
    IconEntry("WifiOff", DarwinIconsExtended.WifiOff),
    IconEntry("Battery", DarwinIconsExtended.Battery),
    IconEntry("BatteryCharging", DarwinIconsExtended.BatteryCharging),
    IconEntry("BatteryFull", DarwinIconsExtended.BatteryFull),
    IconEntry("BatteryLow", DarwinIconsExtended.BatteryLow),
    IconEntry("Tv", DarwinIconsExtended.Tv),
    // Shapes & Status
    IconEntry("Circle", DarwinIconsExtended.Circle),
    IconEntry("CircleAlert", DarwinIconsExtended.CircleAlert),
    IconEntry("CirclePlus", DarwinIconsExtended.CirclePlus),
    IconEntry("CircleMinus", DarwinIconsExtended.CircleMinus),
    IconEntry("CircleDot", DarwinIconsExtended.CircleDot),
    IconEntry("CircleArrowUp", DarwinIconsExtended.CircleArrowUp),
    IconEntry("CircleArrowDown", DarwinIconsExtended.CircleArrowDown),
    IconEntry("CircleArrowLeft", DarwinIconsExtended.CircleArrowLeft),
    IconEntry("CircleArrowRight", DarwinIconsExtended.CircleArrowRight),
    IconEntry("Square", DarwinIconsExtended.Square),
    IconEntry("Triangle", DarwinIconsExtended.Triangle),
    // Security
    IconEntry("Shield", DarwinIconsExtended.Shield),
    IconEntry("ShieldCheck", DarwinIconsExtended.ShieldCheck),
    IconEntry("ShieldAlert", DarwinIconsExtended.ShieldAlert),
    IconEntry("ShieldOff", DarwinIconsExtended.ShieldOff),
    IconEntry("Lock", DarwinIconsExtended.Lock),
    IconEntry("LockOpen", DarwinIconsExtended.LockOpen),
    IconEntry("Key", DarwinIconsExtended.Key),
    IconEntry("KeyRound", DarwinIconsExtended.KeyRound),
    // Layout
    IconEntry("PanelRight", DarwinIconsExtended.PanelRight),
    IconEntry("Grid2x2", DarwinIconsExtended.Grid2x2),
    IconEntry("Grid3x3", DarwinIconsExtended.Grid3x3),
    IconEntry("ListOrdered", DarwinIconsExtended.ListOrdered),
    IconEntry("ListCheck", DarwinIconsExtended.ListCheck),
    IconEntry("Menu", DarwinIconsExtended.Menu),
    IconEntry("EllipsisVertical", DarwinIconsExtended.EllipsisVertical),
    IconEntry("Columns2", DarwinIconsExtended.Columns2),
    IconEntry("Columns3", DarwinIconsExtended.Columns3),
    IconEntry("Rows2", DarwinIconsExtended.Rows2),
    IconEntry("Table", DarwinIconsExtended.Table),
    IconEntry("SlidersHorizontal", DarwinIconsExtended.SlidersHorizontal),
    IconEntry("Layers", DarwinIconsExtended.Layers),
    // Charts
    IconEntry("ChartBar", DarwinIconsExtended.ChartBar),
    IconEntry("ChartLine", DarwinIconsExtended.ChartLine),
    IconEntry("ChartPie", DarwinIconsExtended.ChartPie),
    IconEntry("TrendingUp", DarwinIconsExtended.TrendingUp),
    IconEntry("TrendingDown", DarwinIconsExtended.TrendingDown),
    IconEntry("Activity", DarwinIconsExtended.Activity),
    IconEntry("Gauge", DarwinIconsExtended.Gauge),
    // People
    IconEntry("User", DarwinIconsExtended.User),
    IconEntry("UserPlus", DarwinIconsExtended.UserPlus),
    IconEntry("UserMinus", DarwinIconsExtended.UserMinus),
    IconEntry("Users", DarwinIconsExtended.Users),
    IconEntry("UserRound", DarwinIconsExtended.UserRound),
    IconEntry("Accessibility", DarwinIconsExtended.Accessibility),
    // Commerce
    IconEntry("ShoppingCart", DarwinIconsExtended.ShoppingCart),
    IconEntry("ShoppingBag", DarwinIconsExtended.ShoppingBag),
    IconEntry("CreditCard", DarwinIconsExtended.CreditCard),
    IconEntry("Gift", DarwinIconsExtended.Gift),
    IconEntry("Percent", DarwinIconsExtended.Percent),
    // Transport & Location
    IconEntry("Car", DarwinIconsExtended.Car),
    IconEntry("Plane", DarwinIconsExtended.Plane),
    IconEntry("Rocket", DarwinIconsExtended.Rocket),
    IconEntry("Map", DarwinIconsExtended.Map),
    IconEntry("MapPin", DarwinIconsExtended.MapPin),
    IconEntry("Globe", DarwinIconsExtended.Globe),
    IconEntry("Compass", DarwinIconsExtended.Compass),
    IconEntry("Navigation2", DarwinIconsExtended.Navigation2),
    IconEntry("Locate", DarwinIconsExtended.Locate),
    // Tools
    IconEntry("Wrench", DarwinIconsExtended.Wrench),
    IconEntry("Hammer", DarwinIconsExtended.Hammer),
    IconEntry("Paintbrush", DarwinIconsExtended.Paintbrush),
    IconEntry("Eraser", DarwinIconsExtended.Eraser),
    IconEntry("Ruler", DarwinIconsExtended.Ruler),
    IconEntry("Pipette", DarwinIconsExtended.Pipette),
    IconEntry("Palette", DarwinIconsExtended.Palette),
    IconEntry("Scan", DarwinIconsExtended.Scan),
    IconEntry("QrCode", DarwinIconsExtended.QrCode),
    IconEntry("Barcode", DarwinIconsExtended.Barcode),
    IconEntry("Pin", DarwinIconsExtended.Pin),
    IconEntry("PinOff", DarwinIconsExtended.PinOff),
    // Energy & Nature
    IconEntry("Lightbulb", DarwinIconsExtended.Lightbulb),
    IconEntry("LightbulbOff", DarwinIconsExtended.LightbulbOff),
    IconEntry("Zap", DarwinIconsExtended.Zap),
    IconEntry("ZapOff", DarwinIconsExtended.ZapOff),
    IconEntry("Power", DarwinIconsExtended.Power),
    IconEntry("Flame", DarwinIconsExtended.Flame),
    IconEntry("Leaf", DarwinIconsExtended.Leaf),
    // Text Formatting
    IconEntry("Bold", DarwinIconsExtended.Bold),
    IconEntry("Italic", DarwinIconsExtended.Italic),
    IconEntry("Underline", DarwinIconsExtended.Underline),
    IconEntry("Strikethrough", DarwinIconsExtended.Strikethrough),
    IconEntry("Type", DarwinIconsExtended.Type),
    IconEntry("Code", DarwinIconsExtended.Code),
    IconEntry("Hash", DarwinIconsExtended.Hash),
    IconEntry("AtSign", DarwinIconsExtended.AtSign),
    // Knowledge
    IconEntry("Book", DarwinIconsExtended.Book),
    IconEntry("BookOpen", DarwinIconsExtended.BookOpen),
    IconEntry("GraduationCap", DarwinIconsExtended.GraduationCap),
    IconEntry("Newspaper", DarwinIconsExtended.Newspaper),
    // Health & Fitness
    IconEntry("Stethoscope", DarwinIconsExtended.Stethoscope),
    IconEntry("Pill", DarwinIconsExtended.Pill),
    IconEntry("Syringe", DarwinIconsExtended.Syringe),
    IconEntry("Dumbbell", DarwinIconsExtended.Dumbbell),
    // Misc
    IconEntry("Bug", DarwinIconsExtended.Bug),
    IconEntry("Terminal", DarwinIconsExtended.Terminal),
    IconEntry("Target", DarwinIconsExtended.Target),
    IconEntry("Crosshair", DarwinIconsExtended.Crosshair),
    IconEntry("Hand", DarwinIconsExtended.Hand),
    IconEntry("ThumbsUp", DarwinIconsExtended.ThumbsUp),
    IconEntry("ThumbsDown", DarwinIconsExtended.ThumbsDown),
    IconEntry("Sparkles", DarwinIconsExtended.Sparkles),
    IconEntry("WandSparkles", DarwinIconsExtended.WandSparkles),
    IconEntry("Trophy", DarwinIconsExtended.Trophy),
    IconEntry("Crown", DarwinIconsExtended.Crown),
    IconEntry("Scale", DarwinIconsExtended.Scale),
    IconEntry("Infinity", DarwinIconsExtended.Infinity),
    IconEntry("Repeat", DarwinIconsExtended.Repeat),
    IconEntry("Repeat1", DarwinIconsExtended.Repeat1),
    IconEntry("Shuffle", DarwinIconsExtended.Shuffle),
    IconEntry("Maximize", DarwinIconsExtended.Maximize),
    IconEntry("Minimize", DarwinIconsExtended.Minimize),
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
                    style = DarwinTheme.typography.caption2,
                    color = DarwinTheme.colorScheme.textSecondary,
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

    GalleryPage("Icons", "All available Darwin icons with SF Symbol mapping.") {
        Text(
            text = "On Apple platforms (macOS and iOS), icons are rendered using native SF Symbols. " +
                "On all other platforms (Android, Web), Lucide vector icons are used as fallback.",
            style = DarwinTheme.typography.callout,
            color = DarwinTheme.colorScheme.textSecondary,
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
                style = DarwinTheme.typography.body,
                color = DarwinTheme.colorScheme.textTertiary,
            )
        }
    }
}
