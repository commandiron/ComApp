package com.example.chatapp_by_command.domain.model

data class FriendListRegister(
    var chatRoomUUID: String = "",
    var registerUUID: String ="",
    var requesterEmail: String = "",
    var requesterUUID: String = "",
    var acceptorEmail: String = "",
    var acceptorUUID: String = "",
    var status: String = "",
    var lastMessage: ChatMessage = ChatMessage()
) {
}