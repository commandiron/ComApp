package com.example.chatapp_by_command.view

import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp_by_command.core.SnackbarController
import com.example.chatapp_by_command.domain.model.MyUser
import com.example.chatapp_by_command.domain.model.UserStatus
import com.example.chatapp_by_command.presentation.*
import com.example.chatapp_by_command.presentation.bottomnavigation.BottomNavItem
import com.example.chatapp_by_command.presentation.profile.ClickableToGalleryProfilePictureImage
import com.example.chatapp_by_command.presentation.profile.ProfileAppBar
import com.example.chatapp_by_command.presentation.profile.ProfileViewModel
import com.example.chatapp_by_command.ui.theme.backgroundColor
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalComposeUiApi
@Composable
@InternalCoroutinesApi
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController) {

    //Set SnackBar
    val toastMessage = profileViewModel.toastMessage.value
    LaunchedEffect(key1 = toastMessage){
        if(toastMessage != ""){
            SnackbarController(this).showSnackbar(snackbarHostState,toastMessage, null)
        }
    }

    //Set Progress Indicator
    var isLoading by remember {
        mutableStateOf(false)
    }
    isLoading = profileViewModel.isLoading.value

    //Set User Data From Firebase
    var userDataFromFirebase by remember {mutableStateOf(MyUser())}
    userDataFromFirebase = profileViewModel.userDataStateFromFirebase.value

    var email by remember { mutableStateOf("") }
    email = userDataFromFirebase.userEmail

    var name by remember { mutableStateOf("") }
    name = userDataFromFirebase.userName

    var surName by remember { mutableStateOf("")}
    surName = userDataFromFirebase.userSurName

    var userDataPictureUrl by remember {mutableStateOf("")}
    userDataPictureUrl = userDataFromFirebase.userProfilePictureUrl

    //SignOut Navigate
    val isUserSignOut = profileViewModel.isUserSignOutState.value
    LaunchedEffect(key1 = isUserSignOut){
        if(isUserSignOut){
            navController.popBackStack()
            navController.navigate(BottomNavItem.SignIn.screen_route)
        }
    }

    //ImageUri For Upload Image In Firebase Storage
    var imageUriForFirebaseStorageFromGallery by remember { mutableStateOf<Uri?>(null)}

    //Compose Components
    Column {
        ProfileAppBar {
            profileViewModel.setUserStatusToFirebaseAndSignOut(UserStatus.OFFLINE)
        }

        Surface(
            color = backgroundColor,
            modifier = Modifier
                .fillMaxSize()
                .focusable(true)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { keyboardController.hide() })
                }
                .padding(20.dp)) {

            if(isLoading){
                Box(modifier = Modifier.size(20.dp),contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }else{
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Mail: " + email)

                    ClickableToGalleryProfilePictureImage(userDataPictureUrl){
                        //Profil picture değiştirdiğimde ekranda bir tekleme oluyor, bunu nasıl çözebilirim?
                        //Profil picture boş hali yeşil gözüküyor onu güzel bir boş kafa ile değiştir.
                        imageUriForFirebaseStorageFromGallery = it
                        if(imageUriForFirebaseStorageFromGallery != null){
                            profileViewModel.uploadPictureToFirebase(imageUriForFirebaseStorageFromGallery!!, name, surName)
                        }
                    }

                    OutlinedTextFieldNameCom(name,"Name",{name = it},{

                        profileViewModel.updateProfileToFirebase(userDataPictureUrl,name,surName)
                    })

                    OutlinedTextFieldSurnameCom(surName,"Surname",{surName = it},{
                        profileViewModel.updateProfileToFirebase(userDataPictureUrl,name,surName)
                    })
                }
            }
        }
    }
}





