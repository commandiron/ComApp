package com.example.chatapp_by_command.presentation.login

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp_by_command.domain.model.Response
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.example.chatapp_by_command.domain.use_case.UseCases
import com.example.chatapp_by_command.core.SnackbarController
import com.example.chatapp_by_command.domain.model.UserStatus
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var isUserAuthenticatedState = mutableStateOf<Boolean>(false)
        private set

    var isUserSignInState = mutableStateOf<Boolean>(false)
        private set

    var isUserSignUpState = mutableStateOf<Boolean>(false)
        private set

    var toastMessage = mutableStateOf("")
        private set

    init {
        isUserAuthenticated()
    }


    //PUBLIC FUNCTIONS

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            useCases.signIn(email,password).collect { response ->
                when(response){
                    is Response.Loading -> {
                        toastMessage.value = ""
                    }
                    is Response.Success -> {
                        setUserStatusToFirebase(UserStatus.ONLINE)
                        isUserSignInState.value = response.data
                        toastMessage.value = "Login Successful"
                    }
                    is Response.Error -> {
                        toastMessage.value = "Login Failed"
                    }
                }
            }
        }
    }

    fun signUp(email: String, password: String){
        viewModelScope.launch {
            useCases.signUp(email, password).collect { response ->
                when(response){
                    is Response.Loading -> {
                        toastMessage.value = ""
                    }
                    is Response.Success -> {
                        isUserSignUpState.value = response.data
                        toastMessage.value = "Sign Up Successful"
                        createProfileToFirebase()
                    }
                    is Response.Error -> {
                        toastMessage.value = "Sign Up Failed"
                    }
                }
            }
        }
    }


    //PRIVATE FUNCTIONS

    private fun isUserAuthenticated(){
        viewModelScope.launch {
            useCases.isUserAuthenticated().collect { response ->
                when(response){
                    is Response.Loading -> {}
                    is Response.Success -> {
                        isUserAuthenticatedState.value = response.data
                        if(response.data){
                            setUserStatusToFirebase(UserStatus.ONLINE)
                        }
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    private fun setUserStatusToFirebase(userStatus: UserStatus){
        viewModelScope.launch {
            useCases.setUserStatusToFirebase(userStatus).collect{ response ->
                when(response){
                    is Response.Loading -> {}
                    is Response.Success -> {}
                    is Response.Error -> {}
                }
            }
        }
    }

    private fun createProfileToFirebase(profilePictureUrl: String = "", name: String = "", surName: String = "") {
        viewModelScope.launch {
            useCases.createOrUpdateProfileToFirebase(profilePictureUrl, name, surName).collect { response ->
                when(response){
                    is Response.Loading -> {
                        toastMessage.value = ""
                    }
                    is Response.Success -> {
                        if(response.data){
                            toastMessage.value = "Profile Updated"
                        }else{
                            toastMessage.value = "Profile Saved"
                        }
                    }
                    is Response.Error -> {
                        toastMessage.value = "Update Failed"
                    }
                }
            }
        }
    }
}