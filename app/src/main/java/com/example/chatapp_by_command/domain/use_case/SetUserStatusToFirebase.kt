package com.example.chatapp_by_command.domain.use_case

import com.example.chatapp_by_command.domain.model.enumclasses.UserStatus
import com.example.chatapp_by_command.domain.repository.AppRepository

class SetUserStatusToFirebase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(userStatus: UserStatus)
            = repository.setUserStatusToFirebase(userStatus)
}