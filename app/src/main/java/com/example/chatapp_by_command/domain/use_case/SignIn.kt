package com.example.chatapp_by_command.domain.use_case

import com.example.chatapp_by_command.domain.repository.AppRepository

class SignIn(
    private val repository: AppRepository
) {
    suspend operator fun invoke(email:String, password: String) = repository.signIn(email, password)
}