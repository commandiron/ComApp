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
import kotlinx.coroutines.InternalCoroutinesApi

@Composable
fun NavigationGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController
) {

    AnimatedNavHost(navController, startDestination = BottomNavItem.Splash.fullRoute) {


        //SPLASH SCREEN
        composable(
            BottomNavItem.Splash.fullRoute,
            enterTransition = {
                when(initialState.destination.route){
                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    BottomNavItem.SignIn.fullRoute -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700))
                    else -> null
                }
            }) {

            SplashScreen(
                navController = navController)
        }


        //SIGNIN SCREEN
        composable(
            BottomNavItem.SignIn.fullRoute,
            arguments = listOf(
                navArgument("emailFromSignUp"){
                type = NavType.StringType
                    defaultValue = ""
            }),
            enterTransition = {
                when(initialState.destination.route){
                    BottomNavItem.SignUp.fullRoute->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    BottomNavItem.SignUp.fullRoute -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            }) {

            val emailFromSignUp = remember{
                it.arguments?.getString("emailFromSignUp")
            }

            SignInScreen(
                emailFromSignUp = emailFromSignUp ?: "",
                navController = navController,
                snackbarHostState = snackbarHostState,
                keyboardController = keyboardController)
        }


        //SIGNUP SCREEN
        composable(
            BottomNavItem.SignUp.fullRoute,
            arguments = listOf(
                navArgument("emailFromSignIn"){
                    type = NavType.StringType
                        defaultValue = ""
            }),
            enterTransition = {
                when(initialState.destination.route){
                    BottomNavItem.SignIn.fullRoute ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    BottomNavItem.SignIn.fullRoute ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            }) {

            val emailFromSignIn = remember{
                it.arguments?.getString("emailFromSignIn")
            }
            SignUpScreen(
                emailFromSignIn = emailFromSignIn ?: "",
                navController = navController,
                snackbarHostState = snackbarHostState,
                keyboardController = keyboardController)
        }


        //PROFILE SCREEN
        composable(
            BottomNavItem.Profile.fullRoute,
            enterTransition = {
                when(initialState.destination.route){
                    BottomNavItem.SignIn.fullRoute ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700))
                    BottomNavItem.SignUp.fullRoute ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700))

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


        //USERLIST SCREEN
        composable(
            BottomNavItem.UserList.fullRoute,
            enterTransition = {
                when(initialState.destination.route){
                    BottomNavItem.Chat.fullRoute -> slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    BottomNavItem.Chat.fullRoute -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))

                    else -> null
                }
            }) {
            UserListScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                keyboardController = keyboardController)
        }


        //CHAT SCREEN
        composable(
            BottomNavItem.Chat.fullRoute,
            arguments = listOf(
                navArgument("chatroomUUID"){
                    type = NavType.StringType
                },navArgument("opponentUUID"){
                    type = NavType.StringType
                },navArgument("registerUUID"){
                    type = NavType.StringType
                },navArgument("oneSignalUserId"){
                    type = NavType.StringType
                }),
            enterTransition = {
                when(initialState.destination.route){
                    BottomNavItem.UserList.fullRoute -> slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    BottomNavItem.UserList.fullRoute -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
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
            val oneSignalUserId = remember{
                it.arguments?.getString("oneSignalUserId")
            }

            ChatScreen(
                chatRoomUUID = chatroomUUID?: "",
                opponentUUID = opponentUUID?: "",
                registerUUID= registerUUID?: "",
                oneSignalUserId = oneSignalUserId?: "",
                navController = navController,
                snackbarHostState = snackbarHostState,
                keyboardController = keyboardController)

        }
    }
}