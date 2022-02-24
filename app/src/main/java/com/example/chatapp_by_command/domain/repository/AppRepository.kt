package com.example.chatapp_by_command.domain.repository

import android.net.Uri
import com.example.chatapp_by_command.domain.model.*
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    //Login Screen
    fun isUserAuthenticatedInFirebase(): Flow<Response<Boolean>>
    suspend fun signIn(email: String, password: String): Flow<Response<Boolean>>
    suspend fun signUp(email: String, password: String): Flow<Response<Boolean>>

    //Profile Screen
    suspend fun signOut(): Flow<Response<Boolean>>
    suspend fun uploadPictureToFirebase(uri: Uri, name: String, surName: String): Flow<Response<String>>
    suspend fun createOrUpdateProfileToFirebase(profilePictureUrl: String, name: String, surName: String): Flow<Response<Boolean>>
    suspend fun loadProfileFromFirebase(): Flow<Response<MyUser>>

    suspend fun setUserStatusToFirebase(userStatus: UserStatus): Flow<Response<Boolean>>

    //UserListScreen

    suspend fun loadAcceptedFriendRequestListFromFirebase(): Flow<Response<List<FriendListUiRow>>>
    suspend fun loadPendingFriendRequestListFromFirebase(): Flow<Response<List<FriendListRegister>>>

    suspend fun searchUserFromFirebase(userEmail: String): Flow<Response<MyUser?>>

    suspend fun checkChatRoomIsExistFromFirebase(acceptorUUID: String): Flow<Response<String>>
    suspend fun createChatRoomToFirebase(acceptorUUID: String): Flow<Response<String>>

    suspend fun checkFriendListRegisterIsExistFromFirebase(acceptorEmail: String, acceptorUUID: String): Flow<Response<FriendListRegister>>
    suspend fun createFriendListRegisterToFirebase(chatRoomUUID: String, acceptorEmail: String, acceptorUUID: String): Flow<Response<Boolean>>

    suspend fun acceptPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>>
    suspend fun cancelPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>>
    suspend fun openBlockedFriendToFirebase(registerUUID: String): Flow<Response<Boolean>>

    //ChatScreen
    suspend fun insertMessageToFirebase(chatRoomUUID: String, messageContent: String, registerUUID: String): Flow<Response<Boolean>>
    suspend fun loadMessagesFromFirebase(chatRoomUUID: String, opponentUUID: String, registerUUID: String): Flow<Response<List<ChatMessage>>>
    suspend fun loadOpponentProfileFromFirebase(opponentUUID: String): Flow<Response<MyUser>>
    suspend fun blockFriendToFirebase(registerUUID: String): Flow<Response<Boolean>>

}