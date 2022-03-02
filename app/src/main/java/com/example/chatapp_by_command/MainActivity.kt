package com.example.chatapp_by_command

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.chatapp_by_command.domain.model.enumclasses.UserStatus
import com.example.chatapp_by_command.presentation.bottomnavigation.BottomNavItem
import com.example.chatapp_by_command.ui.theme.*
import com.example.chatapp_by_command.view.BottomNavigationView
import com.example.chatapp_by_command.view.NavigationGraph
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@AndroidEntryPoint
@InternalCoroutinesApi
class MainActivity : ComponentActivity(){

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        mainViewModel = defaultViewModelProviderFactory.create(MainViewModel::class.java)

        setContent {
            AppKeyboardFocusManager()
            ChatApp_by_commandTheme{
                ProvideWindowInsets() {
                    MainScreenView()
                }
            }
        }

    }

    override fun onPause() {
        mainViewModel.setUserStatusToFirebase(UserStatus.OFFLINE)
        super.onPause()
    }

    override fun onResume() {
        mainViewModel.setUserStatusToFirebase(UserStatus.ONLINE)
        super.onResume()
    }

    override fun onDestroy() {
        mainViewModel.setUserStatusToFirebase(UserStatus.OFFLINE)
        super.onDestroy()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalComposeUiApi
@InternalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun MainScreenView(){

    //Set SystemUiController
    val systemUiController = rememberSystemUiController()
    val darkIcons = MaterialTheme.colors.isLight
    SideEffect {
        //System bar kalın neden anlamadım.
        //systemUiController.isNavigationBarContrastEnforced()
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember {SnackbarHostState()}
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
    val bottomBarState = rememberSaveable {(mutableStateOf(false))}

    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.padding(rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyTop = true,
            applyBottom = true,)),
        scaffoldState = scaffoldState,
        bottomBar = {
            bottomBarState.value =
                currentRoute != BottomNavItem.Splash.fullRoute &&
                currentRoute != BottomNavItem.SignIn.fullRoute &&
                currentRoute != BottomNavItem.SignUp.fullRoute &&
                currentRoute != BottomNavItem.Chat.fullRoute

        BottomNavigationView(navController = navController, bottomBarState = bottomBarState.value)}

    ) {
        NavigationGraph(navController,systemUiController,snackbarHostState,keyboardController!!)
    }
}
