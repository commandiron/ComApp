package com.example.chatapp_by_command.domain.use_case

import com.example.chatapp_by_command.domain.repository.AppRepository

class CreateOrUpdateProfileToFirebase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(profilePictureUrl: String, name: String, surName: String)
            = repository.createOrUpdateProfileToFirebase(profilePictureUrl,name,surName)
}