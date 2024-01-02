package eif.viko.lt.faculty.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import eif.viko.lt.faculty.app.presentation.screens.HomeScreen
import eif.viko.lt.faculty.app.presentation.screens.LogInScreen
import eif.viko.lt.faculty.app.presentation.screens.ProfileScreen
import eif.viko.lt.faculty.app.presentation.screens.RegisterScreen
import eif.viko.lt.faculty.app.presentation.navigation.currentRoute
import eif.viko.lt.faculty.app.presentation.navigation.listOfNavItems
import eif.viko.lt.faculty.app.presentation.screens.CreateEventScreen
import eif.viko.lt.faculty.app.presentation.screens.EventScreen
import eif.viko.lt.faculty.app.presentation.screens.ProfileSettingsScreen
import eif.viko.lt.faculty.app.presentation.screens.SavedEventsScreen
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen
import eif.viko.lt.faculty.app.presentation.ui.theme.Primary
import eif.viko.lt.faculty.app.presentation.util.BottomAppBarScreenRoutes
import eif.viko.lt.faculty.app.presentation.util.ScreenRoutes

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val currentRoute = currentRoute(navController = navController)
            var selectedItemIndex by rememberSaveable {
                mutableStateOf(0)
            }

            Scaffold (
                bottomBar = {
                    if (listOfNavItems.any { it.homeRoute == currentRoute }){
                        NavigationBar (
                            containerColor = Primary
                        ){
                            listOfNavItems.forEachIndexed{ index, navItem ->
                                NavigationBarItem(
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = DarkerGreen,
                                        unselectedIconColor = DarkerGreen,
                                        unselectedTextColor = DarkerGreen,
                                        selectedTextColor = DarkerGreen
                                    ),
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        if (selectedItemIndex != index) {
                                            selectedItemIndex = index
                                            navController.navigate(navItem.route){
                                                popUpTo(0)
                                            }
                                        }
                                    },
                                    label = {
                                        Text(text = navItem.label)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if(index == selectedItemIndex) {
                                                navItem.onPressIcon
                                            } else navItem.icon,
                                            contentDescription = navItem.label
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            ){ paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = ScreenRoutes.LOG_IN,
                    modifier = Modifier
                        .padding(paddingValues = paddingValues)
                ){
                    composable(ScreenRoutes.LOG_IN){
                        LogInScreen(
                            onNavigate = {
                                navController.navigate(it.route){
                                    popUpTo(0)
                                }
                            }
                        )
                    }
                    composable(ScreenRoutes.REGISTER){
                        RegisterScreen(
                            onNavigate = {
                                navController.navigate(it.route){
                                    popUpTo(0)
                                }
                            }
                        )
                    }
                    navigation(
                        route = ScreenRoutes.BOTTOM_BAR_NAVIGATION,
                        startDestination = BottomAppBarScreenRoutes.HOME_SCREENS
                    ){
                        navigation(
                            route = BottomAppBarScreenRoutes.HOME_SCREENS,
                            startDestination = BottomAppBarScreenRoutes.HOME
                        ){
                            composable(BottomAppBarScreenRoutes.HOME){
                                HomeScreen(
                                    onNavigate = {
                                        navController.navigate(it.route)
                                    }
                                )
                            }
                            composable(
                                route = BottomAppBarScreenRoutes.EVENT + "?eventId={eventId}",
                                arguments = listOf(
                                    navArgument(name = "eventId"){
                                        type = NavType.StringType
                                    }
                                )
                            ){
                                EventScreen(
                                    onPopBackStack = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                        navigation(
                            route = BottomAppBarScreenRoutes.PROFILE_SCREENS,
                            startDestination = BottomAppBarScreenRoutes.PROFILE
                        ){
                            composable(BottomAppBarScreenRoutes.PROFILE){
                                ProfileScreen(
                                    onNavigate = {
                                        navController.navigate(it.route)
                                    }
                                )
                            }
                            composable(BottomAppBarScreenRoutes.CREATE_EVENT){
                                CreateEventScreen(
                                    onPopBackStack = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                            composable(BottomAppBarScreenRoutes.SAVED_EVENTS){
                                SavedEventsScreen(
                                    onPopBackStack = {
                                        navController.popBackStack()
                                    },
                                    onNavigate = {
                                        navController.navigate(it.route)
                                    }
                                )
                            }
                            composable(BottomAppBarScreenRoutes.PROFILE_SETTINGS){
                                ProfileSettingsScreen(
                                    onPopBackStack = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}