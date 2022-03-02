package com.example.chatapp_by_command.presentation.chat

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.State
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp_by_command.domain.model.ChatMessage
import com.example.chatapp_by_command.domain.model.MyUser
import com.example.chatapp_by_command.domain.model.Response
import com.example.chatapp_by_command.domain.use_case.UseCases
import com.example.chatapp_by_command.core.SnackbarController
import com.example.chatapp_by_command.domain.model.MessageRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel@Inject constructor(
    private val useCases: UseCases
): ViewModel()  {

    var opponentProfileFromFirebase = mutableStateOf(MyUser())
        private set

    var messageInserted = mutableStateOf(false)
        private set

    var messages: List<MessageRegister> by mutableStateOf(listOf())
        private set

    var messagesLoadedFirstTime = mutableStateOf(false)
        private set

    var toastMessage = mutableStateOf("")
        private set


    //PUBLIC FUNCTIONS

    fun insertMessageToFirebase(chatRoomUUID: String, messageContent: String, registerUUID: String, oneSignalUserId : String){
        viewModelScope.launch {
            useCases.insertMessageToFirebase(chatRoomUUID, messageContent, registerUUID, oneSignalUserId).collect { response ->
                when(response){
                    is Response.Loading -> {
                        messageInserted.value = false
                    }
                    is Response.Success -> {
                        messageInserted.value = true
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    fun loadMessagesFromFirebase(chatRoomUUID: String, opponentUUID: String, registerUUID: String){
        viewModelScope.launch {
            useCases.loadMessagesFromFirebase(chatRoomUUID, opponentUUID, registerUUID).collect { response ->
                when(response){
                    is Response.Loading -> {}
                    is Response.Success -> {
                        messages = listOf()
                        for (i in response.data) {
                            if(i.profileUUID == opponentUUID){
                                messages = messages + MessageRegister(i,true) //Opponent Message
                            }else{
                                messages = messages +  MessageRegister(i,false) //User Message
                            }

                        }
                        messagesLoadedFirstTime.value = true
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    fun loadOpponentProfileFromFirebase(opponentUUID: String){
        viewModelScope.launch {
            useCases.loadOpponentProfileFromFirebase(opponentUUID).collect { response ->
                when(response){
                    is Response.Loading -> {}
                    is Response.Success -> {
                        opponentProfileFromFirebase.value = response.data
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    fun blockFriendToFirebase(registerUUID: String){
        viewModelScope.launch {
            useCases.blockFriendToFirebase.invoke(registerUUID).collect { response ->
                when(response){
                    is Response.Loading -> {
                        toastMessage.value = ""
                    }
                    is Response.Success -> {
                        toastMessage.value = "Friend Blocked"
                    }
                    is Response.Error -> {}
                }
            }
        }
    }
}