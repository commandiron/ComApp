package com.example.chatapp_by_command.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.chatapp_by_command.presentation.bottomnavigation.BottomNavItem
import com.example.chatapp_by_command.ui.theme.unselectedContentColor

@Composable
fun BottomNavigationView(navController: NavController, bottomBarState: Boolean) {
    val items = listOf(
        //BottomNavItem.Splash
        //BottomNavItem.SignIn,
        //BottomNavItem.SignUp,
        BottomNavItem.Profile,
        BottomNavItem.UserList
        //BottomNavItem.Chat
    )
    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            androidx.compose.material.BottomNavigation(
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { item ->
                    BottomNavigationItem(
                        icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                        label = { Text(text = item.title,
                            fontSize = 9.sp) },
                        alwaysShowLabel = false,
                        selected = currentRoute == item.screen_route,
                        unselectedContentColor = unselectedContentColor,
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        onClick = {
                            navController.navigate(item.screen_route) {
                                navController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    )
}