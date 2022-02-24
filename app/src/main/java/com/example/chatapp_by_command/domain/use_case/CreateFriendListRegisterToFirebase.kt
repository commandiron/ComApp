package com.example.chatapp_by_command.domain.use_case

import com.example.chatapp_by_command.domain.repository.AppRepository

class CreateFriendListRegisterToFirebase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(chatRoomUUID: String, acceptorEmail: String, acceptorUUID: String)
            = repository.createFriendListRegisterToFirebase(chatRoomUUID,acceptorEmail, acceptorUUID)
}