package com.example.chatapp_by_command.view


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chatapp_by_command.presentation.bottomnavigation.BottomNavItem
import com.example.chatapp_by_command.presentation.chat.ChatViewModel
import com.example.chatapp_by_command.presentation.chat.components.*
import com.example.chatapp_by_command.presentation.chat.components.ChatInput
import com.example.chatapp_by_command.domain.model.enumclasses.MessageStatus
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.chatapp_by_command.core.SnackbarController
import com.example.chatapp_by_command.domain.model.MessageRegister
import com.example.chatapp_by_command.domain.model.MyUser
import com.example.chatapp_by_command.presentation.chat.ProfilePictureDialog
import com.google.accompanist.insets.*
import kotlinx.coroutines.delay


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(
    chatRoomUUID: String,
    opponentUUID: String,
    registerUUID: String,
    oneSignalUserId : String,
    chatViewModel: ChatViewModel = hiltViewModel(),
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController) {

    //Set SnackBar
    val toastMessage = chatViewModel.toastMessage.value
    LaunchedEffect(key1 = toastMessage){
        if(toastMessage != ""){
            SnackbarController(this).showSnackbar(snackbarHostState,toastMessage, null)
        }
    }

    //Performans sorunu var, özellikle son mesaja scrollstate ile scroll etmeyi çalıştırınca bu sorun çıktı.
    //Belki iki ayrı compose üst üste çalıştığı için performans sorunu yaratmış olabilir.

    chatViewModel.loadMessagesFromFirebase(chatRoomUUID, opponentUUID, registerUUID)

    ChatScreenContent(chatRoomUUID, opponentUUID, registerUUID, oneSignalUserId, chatViewModel, navController, keyboardController)
}

@OptIn(ExperimentalComposeUiApi::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
private fun ChatScreenContent(
    chatRoomUUID: String,
    opponentUUID: String,
    registerUUID: String,
    oneSignalUserId : String,
    chatViewModel: ChatViewModel,
    navController: NavHostController,
    keyboardController: SoftwareKeyboardController) {

    //Get Messages
    val messages = chatViewModel.messages

    //Load Oppoenent Profile
    LaunchedEffect(key1 = Unit){
        chatViewModel.loadOpponentProfileFromFirebase(opponentUUID)
    }
    var opponentProfileFromFirebase by remember {mutableStateOf(MyUser())}
    opponentProfileFromFirebase = chatViewModel.opponentProfileFromFirebase.value
    val opponentName = opponentProfileFromFirebase.userName
    val opponentSurname = opponentProfileFromFirebase.userSurName
    val opponentPictureUrl = opponentProfileFromFirebase.userProfilePictureUrl
    val opponentStatus = opponentProfileFromFirebase.status

    //Show Profile Picture
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        ProfilePictureDialog(opponentPictureUrl) {
            showDialog = !showDialog
        }
    }

    //Scroll Lazy Column
    // Bu kısım performansı düşürüyor gibi görünüyor.
    //Chat screen'e ilk girişte screen animasyonu tekliyor. Belki bu kısmı çalıştırmadan delay verebilirim.
    //Ya da kaydırılmış halini hafızada tutabilirim, eğer yapılabiliyorsa. Çünkü her chat screen açıldığında
    //scrollstate ile yukarıdan aşağı kaydırmak zorunda kalıyor. aslında default hali en altta olmalı.
    //Lazy column'u kendim yazabilirim bu sayede scrollstate'i hep aşağıda tutabilirim, içeriden.
    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = messages.size)
    val messagesLoadedFirstTime = chatViewModel.messagesLoadedFirstTime.value
    val messageInserted = chatViewModel.messageInserted.value
    var isChatInputFocus by remember { mutableStateOf(false) } //bunu alarakta klavye alltan çıkınca lazycolumn'u kaydırabiliyorum ama anlık oluyor.
    LaunchedEffect(key1 = messagesLoadedFirstTime, messages,messageInserted){
        if(messages.size > 0){
            scrollState.scrollToItem(
                index = messages.size - 1)
        }
    }
    val imePaddingValues = rememberInsetsPaddingValues(insets = LocalWindowInsets.current.ime)
    val imeBottomPadding = imePaddingValues.calculateBottomPadding().value.toInt()
    //Klavye alttan çekince swipe etme olayını çözdüm fakat çok atlıyor. Smooth değil. Özellikle kapatırken. Ara değerleri çok hesaplamadığı için olabilir.
    LaunchedEffect(key1 = imeBottomPadding){
        if(messages.size > 0){
            scrollState.scrollToItem(
                index = messages.size - 1) }
    }

    //Compose Components
    Column(
        modifier = Modifier
            .fillMaxSize()
            .focusable()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { keyboardController.hide() })
            }
            .background(Color(0xffFBE9E7))
    ) {

        val context = LocalContext.current

        ChatAppBar(
            title = "$opponentName $opponentSurname",
            description = opponentStatus.lowercase(),
            pictureUrl = opponentPictureUrl,
            onUserNameClick = {
                Toast.makeText(context, "User Profile Clicked", Toast.LENGTH_SHORT).show()
            }, onBackArrowClick = {
                navController.popBackStack()
                navController.navigate(BottomNavItem.UserList.fullRoute)
            }, onUserProfilePictureClick = {
                showDialog = true
            }, onMorevertBlockUserClick = {
                chatViewModel.blockFriendToFirebase(registerUUID)
                navController.navigate(BottomNavItem.UserList.fullRoute)
            }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
            state = scrollState
        ) {
            items(messages) { message: MessageRegister ->

                val sdf = remember { SimpleDateFormat("hh:mm", Locale.ROOT) }

                when (message.isMessageFromOpponent){

                    true -> { //Opponent Message
                        ReceivedMessageRowAlt(
                            text = message.chatMessage.message,
                            opponentName = opponentName,
                            quotedMessage = null,
                            messageTime = sdf.format(message.chatMessage.date),
                        )
                    }

                    false ->{ //User Message
                        SentMessageRowAlt(
                            text = message.chatMessage.message,
                            quotedMessage = null,
                            messageTime = sdf.format(message.chatMessage.date),
                            messageStatus = MessageStatus.valueOf(message.chatMessage.status)
                        )
                    }

                }
            }
        }

        ChatInput(
            onMessageChange = { messageContent ->
                chatViewModel.insertMessageToFirebase(chatRoomUUID,messageContent,registerUUID, oneSignalUserId)},
            modifier = Modifier.background(MaterialTheme.colors.primary), onFocusEvent = {
                isChatInputFocus = it
            }
        )
    }
}