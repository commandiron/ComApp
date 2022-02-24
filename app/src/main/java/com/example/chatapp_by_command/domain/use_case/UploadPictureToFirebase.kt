package com.example.chatapp_by_command.domain.use_case

import android.net.Uri
import com.example.chatapp_by_command.domain.repository.AppRepository

class UploadPictureToFirebase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(uri: Uri, name: String, surName: String) = repository.uploadPictureToFirebase(uri, name, surName)
}