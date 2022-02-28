package com.example.chatapp_by_command.domain.model

data class MyUser(
    var profileUUID: String = "",
    var userEmail: String = "",
    var userName: String = "",
    var userProfilePictureUrl: String = "",
    var userSurName: String = "",
    var status: String = ""
)