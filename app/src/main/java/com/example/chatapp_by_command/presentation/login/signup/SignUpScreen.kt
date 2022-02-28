package com.example.chatapp_by_command.presentation.login.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
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
import com.example.chatapp_by_command.presentation.LoginOutlinedTextFieldCom
import com.example.chatapp_by_command.presentation.bottomnavigation.BottomNavItem
import com.example.chatapp_by_command.presentation.common_components.LoginOutlinedTextFieldPasswordCom
import com.example.chatapp_by_command.presentation.login.LoginViewModel
import com.example.chatapp_by_command.ui.theme.backgroundColor
import com.example.chatapp_by_command.ui.theme.backgroundColorDark
import com.example.chatapp_by_command.ui.theme.primaryColor
import kotlinx.coroutines.InternalCoroutinesApi


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
@InternalCoroutinesApi
fun SignUpScreen(
    emailFromSignIn: String,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController
) {

    //Set SnackBar
    val toastMessage = loginViewModel.toastMessage.value
    LaunchedEffect(key1 = toastMessage){
        if(toastMessage != ""){
            SnackbarController(this).showSnackbar(snackbarHostState,toastMessage, null)
        }
    }

    //For test user information
    var textEmail: String? by remember { mutableStateOf("") }//gimli@gmail.com
    var textPassword: String? by remember { mutableStateOf("") }//123456
    textEmail = emailFromSignIn

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
            navController.navigate(BottomNavItem.Profile.fullRoute)
        }
    }

    //Sign Up Navigate
    val isUserSignUp = loginViewModel.isUserSignUpState.value
    LaunchedEffect(key1 = isUserSignUp){
        if (isUserSignUp) {
            navController.navigate(BottomNavItem.Profile.fullRoute)
        }
    }

    //Compose Components
    Column() {
        Surface(
            color = backgroundColor,
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
                modifier = Modifier.padding(bottom = 30.dp)) {

                Icon(
                    modifier = Modifier
                        .size(120.dp),
                    imageVector = Icons.Default.Chat,
                    contentDescription = "Logo Icon",
                    tint = primaryColor
                )

                Text(text ="Sign up for ComApp",
                    fontFamily = FontFamily.Cursive,
                    fontSize = 36.sp,
                    modifier = Modifier.padding(2.dp, 2.dp, 2.dp, 2.dp))

                Text(text ="A simple chat app.",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(2.dp, 2.dp, 2.dp, 30.dp))

                Box(modifier = Modifier.padding(2.dp)){
                    LoginOutlinedTextFieldCom(textEmail!!,"Email", Icons.Default.Email) {
                        textEmail = it
                    }
                }
                Box(modifier = Modifier.padding(2.dp)){
                    LoginOutlinedTextFieldPasswordCom(textPassword!!,"Password", Icons.Default.Password) {
                        textPassword = it
                    }
                }

                Button(onClick = {
                    loginViewModel.signUp(textEmail!!, textPassword!!)
                },modifier = Modifier.padding(2.dp)) {
                    Text(
                        text = "Sign Up",
                        color = Color.White)
                }
            }
        }

        Surface(
            color = backgroundColorDark,
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
                Text(text = "Already have an account?", fontSize = 14.sp, color = Color.Black)
                Text(text = " Log in", fontSize = 14.sp, color = Color.Red, modifier = Modifier.clickable {

                    if(textEmail == ""){
                        navController.popBackStack()
                        navController.navigate(BottomNavItem.SignIn.fullRoute)
                    }else{
                        navController.popBackStack()
                        navController.navigate(BottomNavItem.SignIn.screen_route + "?emailFromSignUp=$textEmail")
                    }
                })
            }
        }
    }
}