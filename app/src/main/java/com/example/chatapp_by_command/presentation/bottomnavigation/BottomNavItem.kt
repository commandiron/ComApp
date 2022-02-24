package com.example.chatapp_by_command.presentation.bottomnavigation

import com.example.chatapp_by_command.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Splash: BottomNavItem("Splash",R.drawable.ic_launcher_foreground,"splash")
    object SignIn: BottomNavItem("SignIn",R.drawable.ic_launcher_foreground,"signin")
    object SignUp: BottomNavItem("SignUp",R.drawable.ic_launcher_foreground,"signup")
    object Profile : BottomNavItem("Profile", R.drawable.ic_baseline_home_24,"profile")
    object UserList: BottomNavItem("Chat",R.drawable.ic_baseline_chat_24,"userlist")
    object Chat: BottomNavItem("Chat",R.drawable.ic_baseline_chat_24,"chat")

}
