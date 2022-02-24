package com.example.chatapp_by_command.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class MyUser(
    var profileUUID: String = "",
    var userEmail: String = "",
    var userName: String = "",
    var userProfilePictureUrl: String = "",
    var userSurName: String = "",
    var status: String = ""
)