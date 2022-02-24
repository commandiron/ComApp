package com.example.chatapp_by_command.domain.use_case

import com.example.chatapp_by_command.domain.model.ChatMessage
import com.example.chatapp_by_command.domain.repository.AppRepository

class InsertMessageToFirebase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(chatRoomUUID: String, messageContent: String, registerUUID: String)
    = repository.insertMessageToFirebase(chatRoomUUID, messageContent, registerUUID)
}