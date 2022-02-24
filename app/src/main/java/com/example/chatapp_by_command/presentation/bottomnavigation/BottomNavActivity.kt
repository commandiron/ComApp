package com.example.chatapp_by_command.view

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.chatapp_by_command.presentation.bottomnavigation.BottomNavItem
import com.example.chatapp_by_command.presentation.login.signup.SignUpScreen
import com.example.chatapp_by_command.presentation.splash.SplashScreen
import com.example.chatapp_by_command.presentation.userlist.UserListScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.systemuicontroller.SystemUiController
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
@InternalCoroutinesApi
fun NavigationGraph(
    navController: NavHostController,
    systemUiController: SystemUiController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController
) {

    AnimatedNavHost(navController, startDestination = BottomNavItem.Splash.screen_route) {

        composable(
            BottomNavItem.Splash.screen_route,
            enterTransition = {
                when(initialState.destination.route){
                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    BottomNavItem.SignIn.screen_route -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700))
                    else -> null
                }
            }) {

            SplashScreen(
                navController = navController,
                systemUiController = systemUiController)
        }

        composable(
            BottomNavItem.SignIn.screen_route,
            enterTransition = {
                when(initialState.destination.route){
                    BottomNavItem.SignUp.screen_route -> slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    BottomNavItem.SignUp.screen_route -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            }) {

            SignInScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                keyboardController = keyboardController)
        }

        composable(
            BottomNavItem.SignUp.screen_route,
            enterTransition = {
                when(initialState.destination.route){
                    BottomNavItem.SignIn.screen_route -> slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    BottomNavItem.SignIn.screen_route -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            }) {
            SignUpScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                keyboardController = keyboardController)
        }

        composable(
            BottomNavItem.Profile.screen_route,
            enterTransition = {
                when(initialState.destination.route){
                    BottomNavItem.SignIn.screen_route -> slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700))
                    BottomNavItem.SignUp.screen_route -> slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700))

                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    else -> null
                }
            }) {
            ProfileScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                keyboardController= keyboardController)
        }

        composable(
            BottomNavItem.UserList.screen_route,
            enterTransition = {
                when(initialState.destination.route){
                    BottomNavItem.Chat.screen_route + "/{chatroomUUID}" + "/{opponentUUID}" + "/{registerUUID}" -> slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    BottomNavItem.Chat.screen_route + "/{chatroomUUID}" + "/{opponentUUID}" + "/{registerUUID}" -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))

                    else -> null
                }
            }) {
            UserListScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                keyboardController = keyboardController)
        }

        composable(
            BottomNavItem.Chat.screen_route + "/{chatroomUUID}" + "/{opponentUUID}" + "/{registerUUID}",
            arguments = listOf(
                navArgument("chatroomUUID"){
                    type = NavType.StringType
                },navArgument("opponentUUID"){
                    type = NavType.StringType
                },navArgument("registerUUID"){
                    type = NavType.StringType
                }),
            enterTransition = {
                when(initialState.destination.route){
                    BottomNavItem.UserList.screen_route -> slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    BottomNavItem.UserList.screen_route -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            }) {

            val chatroomUUID = remember{
                it.arguments?.getString("chatroomUUID")
            }
            val opponentUUID = remember{
                it.arguments?.getString("opponentUUID")
            }

            val registerUUID = remember{
                it.arguments?.getString("registerUUID")
            }

            ChatScreen(
                chatRoomUUID = chatroomUUID?: "",
                opponentUUID = opponentUUID?: "",
                registerUUID= registerUUID?: "",
                navController = navController,
                snackbarHostState = snackbarHostState,
                keyboardController = keyboardController!!)

        }
    }
}