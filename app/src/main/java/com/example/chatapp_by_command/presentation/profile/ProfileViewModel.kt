package com.example.chatapp_by_command.presentation.profile


import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.example.chatapp_by_command.domain.model.Response
import com.example.chatapp_by_command.domain.model.MyUser
import com.example.chatapp_by_command.domain.model.enumclasses.UserStatus
import com.example.chatapp_by_command.domain.use_case.UseCases
import kotlinx.coroutines.delay
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var toastMessage = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    var isUserSignOutState = mutableStateOf(false)
        private set

    var userDataStateFromFirebase = mutableStateOf(MyUser())
        private set

    init {
        loadProfileFromFirebase()
    }

    //PUBLIC FUNCTIONS

    fun setUserStatusToFirebaseAndSignOut(userStatus: UserStatus){
        viewModelScope.launch {
            useCases.setUserStatusToFirebase(userStatus).collect{ response ->
                when(response){
                    is Response.Loading -> {}
                    is Response.Success -> {
                        if(response.data){
                            signOut()
                        }else{
                            //Auth.currentuser null.
                        }

                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    fun uploadPictureToFirebase(uri: Uri) {
        viewModelScope.launch {
            useCases.uploadPictureToFirebase(uri).collect { response ->
                when(response){
                    is Response.Loading -> {
                        isLoading.value = true
                    }
                    is Response.Success -> {
                        //Picture Uploaded
                        isLoading.value = false
                        updateProfileToFirebase(
                            MyUser(userProfilePictureUrl = response.data))
                    }
                    is Response.Error -> {}
                }

            }
        }
    }

    fun updateProfileToFirebase(myUser: MyUser) {
        viewModelScope.launch {
            useCases.createOrUpdateProfileToFirebase(myUser).collect { response ->
                when(response){
                    is Response.Loading -> {
                        toastMessage.value = ""
                        isLoading.value = true
                    }
                    is Response.Success -> {
                        isLoading.value = false
                        if(response.data){
                            toastMessage.value = "Profile Updated"
                        }else{
                            toastMessage.value = "Profile Saved"
                        }
                        //delay(2000) //Bu ne içindi hatırlayamadım.
                        loadProfileFromFirebase()
                    }
                    is Response.Error -> {
                        toastMessage.value = "Update Failed"
                    }
                }
            }
        }
    }


    //PRIVATE FUNCTIONS

    private fun signOut() {
        viewModelScope.launch {
            useCases.signOut().collect { response ->
                when(response) {
                    is Response.Loading -> {
                        toastMessage.value = ""
                    }
                    is Response.Success -> {
                        isUserSignOutState.value = response.data
                        toastMessage.value = "Sign Out"
                    }
                    is Response.Error -> Log.d(ContentValues.TAG, response.message)
                }

            }
        }
    }

    private fun loadProfileFromFirebase() {
        viewModelScope.launch {
            useCases.loadProfileFromFirebase().collect { response ->
                when(response){
                    is Response.Loading -> {
                        isLoading.value = true
                    }
                    is Response.Success -> {
                        userDataStateFromFirebase.value = response.data
                        delay(500)
                        isLoading.value = false
                    }
                    is Response.Error -> {}
                }
            }
        }
    }
}