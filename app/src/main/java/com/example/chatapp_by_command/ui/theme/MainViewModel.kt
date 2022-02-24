package com.example.chatapp_by_command.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp_by_command.domain.model.Response
import com.example.chatapp_by_command.domain.model.UserStatus
import com.example.chatapp_by_command.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val useCases: UseCases
): ViewModel()  {

    //PUBLIC FUNCTIONS

    fun setUserStatusToFirebase(userStatus: UserStatus){
        viewModelScope.launch {
            useCases.setUserStatusToFirebase(userStatus).collect{ response ->
                when(response){
                    is Response.Loading -> {}
                    is Response.Success -> {
                    }
                    is Response.Error -> {}
                }
            }
        }
    }
}