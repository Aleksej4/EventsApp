package eif.viko.lt.faculty.app.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import eif.viko.lt.faculty.app.presentation.util.BottomAppBarScreenRoutes

data class NavItem (
    val label: String,
    val icon: ImageVector,
    val onPressIcon: ImageVector,
    val route: String,
    val homeRoute: String,
)

val listOfNavItems: List<NavItem> = listOf(
    NavItem(
        label = "Home",
        icon = Icons.Outlined.Home,
        onPressIcon = Icons.Filled.Home,
        route = BottomAppBarScreenRoutes.HOME_SCREENS,
        homeRoute = BottomAppBarScreenRoutes.HOME
    ),
    NavItem(
        label = "Profile",
        icon = Icons.Outlined.AccountCircle,
        onPressIcon = Icons.Filled.AccountCircle,
        route = BottomAppBarScreenRoutes.PROFILE_SCREENS,
        homeRoute = BottomAppBarScreenRoutes.PROFILE
    )
)