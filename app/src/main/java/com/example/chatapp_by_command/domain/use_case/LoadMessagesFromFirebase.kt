package com.example.chatapp_by_command.domain.use_case

import com.example.chatapp_by_command.domain.model.ChatMessage
import com.example.chatapp_by_command.domain.repository.AppRepository

class LoadMessagesFromFirebase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(chatRoomUUID: String, opponentUUID: String,  registerUUID: String)
    = repository.loadMessagesFromFirebase(chatRoomUUID, opponentUUID, registerUUID)
}