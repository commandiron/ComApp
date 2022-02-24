package com.example.chatapp_by_command.domain.use_case

import com.example.chatapp_by_command.domain.repository.AppRepository

class CheckFriendListRegisterIsExistFromFirebase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(acceptorEmail: String, acceptorUUID: String)
    = repository.checkFriendListRegisterIsExistFromFirebase(acceptorEmail,acceptorUUID)
}