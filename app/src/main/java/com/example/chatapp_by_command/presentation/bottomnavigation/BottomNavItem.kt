package com.example.chatapp_by_command.presentation.bottomnavigation

import com.example.chatapp_by_command.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String, var arguments: String){

    object Splash: BottomNavItem("Splash",R.drawable.ic_launcher_foreground,"splash", ""){
        val fullRoute = screen_route + arguments
    }
    object SignIn: BottomNavItem("SignIn",R.drawable.ic_launcher_foreground,"signin", "?emailFromSignUp={emailFromSignUp}"){
        val fullRoute = screen_route + arguments
    }
    object SignUp: BottomNavItem("SignUp",R.drawable.ic_launcher_foreground,"signup", "?emailFromSignIn={emailFromSignIn}"){
        val fullRoute = screen_route + arguments
    }
    object Profile : BottomNavItem("Profile", R.drawable.ic_baseline_home_24,"profile", ""){
        val fullRoute = screen_route + arguments
    }
    object UserList: BottomNavItem("Chat",R.drawable.ic_baseline_chat_24,"userlist", ""){
        val fullRoute = screen_route + arguments
    }
    object Chat: BottomNavItem("Chat",R.drawable.ic_baseline_chat_24,"chat","/{chatroomUUID}" + "/{opponentUUID}" + "/{registerUUID}" + "/{oneSignalUserId}"){
        val fullRoute = screen_route + arguments
    }



}

