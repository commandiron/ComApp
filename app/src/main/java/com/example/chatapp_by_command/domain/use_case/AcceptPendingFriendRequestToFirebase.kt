package com.example.chatapp_by_command.domain.use_case

import com.example.chatapp_by_command.domain.repository.AppRepository

class AcceptPendingFriendRequestToFirebase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(registerUUID: String) = repository.acceptPendingFriendRequestToFirebase(registerUUID)
}