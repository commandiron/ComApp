package com.example.chatapp_by_command.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp_by_command.core.SnackbarController
import com.example.chatapp_by_command.presentation.LoginEmailCustomOutlinedTextField
import com.example.chatapp_by_command.presentation.LoginPasswordCustomOutlinedTextField
import com.example.chatapp_by_command.presentation.bottomnavigation.BottomNavItem
import com.example.chatapp_by_command.presentation.login.LoginViewModel
import kotlinx.coroutines.InternalCoroutinesApi


@Composable
fun SignInScreen(
    emailFromSignUp: String,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController) {

    //Set SnackBar
    val toastMessage = loginViewModel.toastMessage.value
    LaunchedEffect(key1 = toastMessage){
        if(toastMessage != ""){
            SnackbarController(this).showSnackbar(snackbarHostState,toastMessage, "Close")
        }
    }

    //For test user information
    var textEmail: String? by remember {mutableStateOf("")}//gimli@gmail.com
    var textPassword: String? by remember { mutableStateOf("") }//123456

    LaunchedEffect(key1 = Unit){
        textEmail = emailFromSignUp
    }

    //Check User Authenticated
    val isUserAuthenticated = loginViewModel.isUserAuthenticatedState.value
    LaunchedEffect(Unit) {
        if(isUserAuthenticated) {
            navController.navigate(BottomNavItem.Profile.fullRoute)
        }
    }

    //Sign In Navigate
    val isUserSignIn = loginViewModel.isUserSignInState.value
    LaunchedEffect(key1 = isUserSignIn){
        if (isUserSignIn) {
            keyboardController.hide()
            navController.navigate(BottomNavItem.Profile.fullRoute)
        }
    }

    //Compose Components
    Column() {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier
                .weight(8f)
                .fillMaxSize()
                .focusable(true)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        keyboardController.hide()
                    })
                }
                .padding(20.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 120.dp)) {

                Icon(
                    modifier = Modifier
                        .size(120.dp),
                    imageVector = Icons.Default.Chat,
                    contentDescription = "Logo Icon",
                    tint = MaterialTheme.colors.onBackground
                )

                Text(text ="Log in to ComApp",
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 36.sp,
                    modifier = Modifier.padding(2.dp, 2.dp, 2.dp, 2.dp))

                Text(text ="A simple chat app.",
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(2.dp, 2.dp, 2.dp, 30.dp))

                Box(modifier = Modifier.padding(2.dp)){
                    LoginEmailCustomOutlinedTextField(textEmail!!,"Email", Icons.Default.Email) {
                        textEmail = it
                    }
                }

                Box(modifier = Modifier.padding(2.dp)){
                    LoginPasswordCustomOutlinedTextField(textPassword!!,"Password", Icons.Default.Password) {
                        textPassword = it
                    }
                }

                Button(onClick = {
                    loginViewModel.signIn(textEmail!!,textPassword!!)
                },modifier = Modifier.padding(2.dp)) {
                    Text(
                        text = "Log In",
                        color = MaterialTheme.colors.onPrimary)
                }
            }
        }

        Surface(
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .focusable(true)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { keyboardController.hide() })
                }) {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 30.dp)) {
                Text(text = "Don't have an account?", fontSize = 14.sp, color = MaterialTheme.colors.onBackground)
                Text(text = " Sign up", fontSize = 14.sp, color = Color.Red, modifier = Modifier.clickable {

                    if(textEmail == ""){
                        navController.popBackStack()
                        navController.navigate(BottomNavItem.SignUp.fullRoute)
                    }else{
                        navController.popBackStack()
                        navController.navigate(BottomNavItem.SignUp.screen_route + "?emailFromSignIn=$textEmail")
                    }
                })
            }
        }
    }
}




