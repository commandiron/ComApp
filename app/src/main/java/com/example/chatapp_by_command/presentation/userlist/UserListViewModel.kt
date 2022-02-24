package com.example.chatapp_by_command.presentation.userlist

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp_by_command.core.Constants
import com.example.chatapp_by_command.domain.model.FriendListRegister
import com.example.chatapp_by_command.domain.model.FriendListUiRow
import com.example.chatapp_by_command.domain.model.FriendStatus
import com.example.chatapp_by_command.domain.model.Response
import com.example.chatapp_by_command.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var pendingFriendRequestList = mutableStateOf<List<FriendListRegister>>(listOf())
        private set

    var acceptedFriendRequestList = mutableStateOf<List<FriendListUiRow>>(listOf())
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    var toastMessage = mutableStateOf("")
        private set


    //PUBLIC FUNCTIONS

    fun refreshFriendList(){
        viewModelScope.launch {
            isRefreshing.value = true
            loadPendingFriendRequestListFromFirebase()
            loadAcceptedFriendRequestListFromFirebase()
            delay(1000)
            isRefreshing.value = false
        }
    }

    fun createFriendshipRegisterToFirebase(acceptorEmail: String){

        //Search User -> Check Chat Room -> Create Chat Room -> Check FriendListRegister -> Create FriendListRegister

        viewModelScope.launch {
            useCases.searchUserFromFirebase.invoke(acceptorEmail).collect { response ->
                when(response){
                    is Response.Loading -> {
                        toastMessage.value = ""
                    }
                    is Response.Success -> {
                        if(response.data != null){
                            checkChatRoomIsExistFromFirebaseAndCreateIfNot(acceptorEmail, response.data.profileUUID)
                        }else{
                            toastMessage.value = "User Not Found"
                        }
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    fun acceptPendingFriendRequestToFirebase(registerUUID: String){
        viewModelScope.launch {
            useCases.acceptedPendingFriendRequestToFirebase.invoke(registerUUID).collect { response ->
                when(response){
                    is Response.Loading -> {
                        toastMessage.value = ""
                    }
                    is Response.Success -> {
                        toastMessage.value = "Friend Request Accepted."
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    fun cancelPendingFriendRequestToFirebase(registerUUID: String){
        viewModelScope.launch {
            useCases.cancelPendingFriendRequestToFirebase.invoke(registerUUID).collect { response ->
                when(response){
                    is Response.Loading -> {
                        toastMessage.value = ""
                    }
                    is Response.Success -> {
                        toastMessage.value = "Friend Request Canceled."
                    }
                    is Response.Error -> {}
                }
            }
        }
    }


    //PRIVATE FUNCTIONS

    private fun loadAcceptedFriendRequestListFromFirebase(){
        viewModelScope.launch {
            useCases.loadAcceptedFriendRequestListFromFirebase.invoke().collect { response ->
                when(response){
                    is Response.Loading -> {
                    }
                    is Response.Success -> {
                        if(response.data.size != 0){
                            acceptedFriendRequestList.value = response.data
                        }
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    private fun loadPendingFriendRequestListFromFirebase(){
        viewModelScope.launch {
            useCases.loadPendingFriendRequestListFromFirebase.invoke().collect { response ->
                when(response){
                    is Response.Loading -> {
                    }
                    is Response.Success -> {
                        pendingFriendRequestList.value = response.data
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    private fun checkChatRoomIsExistFromFirebaseAndCreateIfNot(acceptorEmail: String, acceptorUUID: String){
        viewModelScope.launch {
            useCases.checkChatRoomIsExistFromFirebase.invoke(acceptorUUID).collect { response ->
                when(response){
                    is Response.Loading -> {}
                    is Response.Success -> {
                        if(response.data.equals(Constants.NO_CHATROOM_IN_FIREBASE_DATABASE)){
                            //Create ChatRoom
                            createChatRoomToFirebase(acceptorEmail, acceptorUUID)

                        }else{
                            //Chat Room Exist
                            checkFriendListRegisterIsExistFromFirebase(response.data,acceptorEmail,acceptorUUID)
                        }
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    private fun createChatRoomToFirebase(acceptorEmail: String,acceptorUUID: String){
        viewModelScope.launch {
            useCases.createChatRoomToFirebase.invoke(acceptorUUID).collect { response ->
                when(response){
                    is Response.Loading -> {}
                    is Response.Success -> {
                        //Chat Room Created.
                        checkFriendListRegisterIsExistFromFirebase(response.data,acceptorEmail,acceptorUUID)
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    private fun checkFriendListRegisterIsExistFromFirebase(chatRoomUUID: String, acceptorEmail: String, acceptorUUID: String){
        viewModelScope.launch {
            useCases.checkFriendListRegisterIsExistFromFirebase.invoke(acceptorEmail, acceptorUUID).collect { response ->
                when(response){
                    is Response.Loading -> {
                        toastMessage.value = ""
                    }
                    is Response.Success -> {
                        if(response.data.equals(FriendListRegister())){
                            toastMessage.value = "Friend Request Sent."
                            createFriendListRegisterToFirebase(chatRoomUUID, acceptorEmail,acceptorUUID)
                        }else if (response.data.status.equals(FriendStatus.PENDING.toString())){
                            toastMessage.value = "Already Have Friend Request"
                        }else if (response.data.status.equals(FriendStatus.ACCEPTED.toString())){
                            toastMessage.value = "You Are Already Friend."
                        }else if(response.data.status.equals(FriendStatus.BLOCKED.toString())){
                            openBlockedFriendToFirebase(response.data.registerUUID)
                        }
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    private fun createFriendListRegisterToFirebase(chatRoomUUID: String, acceptorEmail: String, acceptorUUID: String){
        viewModelScope.launch {
            useCases.createFriendListRegisterToFirebase.invoke(chatRoomUUID, acceptorEmail, acceptorUUID).collect { response ->
                when(response){
                    is Response.Loading -> {}
                    is Response.Success -> {
                    }
                    is Response.Error -> {}
                }

            }
        }
    }

    private fun openBlockedFriendToFirebase(registerUUID: String){
        viewModelScope.launch {
            useCases.openBlockedFriendToFirebase.invoke(registerUUID).collect { response ->
                when(response){
                    is Response.Loading -> {
                        toastMessage.value = ""
                    }
                    is Response.Success -> {
                        if(response.data){
                            toastMessage.value = "User Block Opened And Accept As Friend"
                        }else{
                            toastMessage.value = "You Are Blocked by User"
                        }

                    }
                    is Response.Error -> {}
                }
            }
        }
    }
}