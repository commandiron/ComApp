package com.example.chatapp_by_command.domain.model

data class SnackbarMessage(
    var show: Boolean = false,
    var message: String = "",
) {
}