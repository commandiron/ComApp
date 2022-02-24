package com.example.chatapp_by_command.domain.model

import com.example.chatapp_by_command.presentation.chat.components.chatrow.MessageStatus

data class ChatMessage(
    val profileUUID: String = "",
    var message: String = "",
    var date: Long = 0,
    var status: String = "",
)