package com.example.chatapp_by_command.domain.use_case

import com.example.chatapp_by_command.domain.model.MyUser
import com.example.chatapp_by_command.domain.repository.AppRepository

class CreateOrUpdateProfileToFirebase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(myUser: MyUser)
            = repository.createOrUpdateProfileToFirebase(myUser)
}