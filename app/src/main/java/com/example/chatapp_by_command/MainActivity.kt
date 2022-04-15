package com.example.chatapp_by_command

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.chatapp_by_command.core.Constants
import com.example.chatapp_by_command.domain.model.enumclasses.UserStatus
import com.example.chatapp_by_command.presentation.bottomnavigation.BottomNavItem
import com.example.chatapp_by_command.presentation.common_components.CustomSnackbar
import com.example.chatapp_by_command.ui.theme.*
import com.example.chatapp_by_command.view.BottomNavigationView
import com.example.chatapp_by_command.view.NavigationGraph
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.onesignal.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), OSSubscriptionObserver{

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //MainViewModel For User Status
        mainViewModel = defaultViewModelProviderFactory.create(MainViewModel::class.java)

        //For Insets library
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(Constants.ONESIGNAL_APP_ID)

        // OneSignal Enable Notification -> Notificationu app'e focus olunca kapatamıyorum. Daha sonra tekrardan bak.
        OneSignal.addSubscriptionObserver(this)
        OneSignal.disablePush(false)

        setContent {
            AppKeyboardFocusManager()
            ChatApp_by_commandTheme{
                ProvideWindowInsets() {
                    MainScreenView()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        mainViewModel.setUserStatusToFirebase(UserStatus.ONLINE)
        super.onResume()
    }

    override fun onPause() {
        mainViewModel.setUserStatusToFirebase(UserStatus.OFFLINE)
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        mainViewModel.setUserStatusToFirebase(UserStatus.OFFLINE)
        super.onDestroy()
    }

    override fun onOSSubscriptionChanged(p0: OSSubscriptionStateChanges?) {

        if (p0!!.from.isSubscribed &&
            !p0.to.isSubscribed
        ) {
            println("Notifications Disabled!")
        }
        if (!p0.from.isSubscribed &&
            p0.to.isSubscribed
        ) {
            println("Notifications Enabled!")
        }
    }
}

@Composable
fun MainScreenView(){

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
            applyBottom = true,
        )),
        scaffoldState = scaffoldState,
        snackbarHost = {
           SnackbarHost(hostState = it){ data ->
                CustomSnackbar(
                    snackbarData = data
                )
           }
        },
        bottomBar = {
            bottomBarState.value =
                currentRoute != BottomNavItem.Splash.fullRoute &&
                currentRoute != BottomNavItem.SignIn.fullRoute &&
                currentRoute != BottomNavItem.SignUp.fullRoute &&
                currentRoute != BottomNavItem.Chat.fullRoute

            BottomNavigationView(navController = navController, bottomBarState = bottomBarState.value)
        }

    ) {
        NavigationGraph(navController,snackbarHostState,keyboardController!!)
    }
}
