package com.example.chatapp_by_command.domain.use_case

import com.example.chatapp_by_command.domain.repository.AppRepository

class CheckChatRoomIsExistFromFirebase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(acceptorUUID: String) = repository.checkChatRoomIsExistFromFirebase(acceptorUUID)
}