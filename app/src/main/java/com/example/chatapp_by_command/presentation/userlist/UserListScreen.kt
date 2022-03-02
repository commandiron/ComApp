package com.example.chatapp_by_command.presentation.userlist

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp_by_command.core.SnackbarController
import com.example.chatapp_by_command.presentation.bottomnavigation.BottomNavItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserListScreen(
    userListViewModel: UserListViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController) {

    //Belki bu screen'de listede mailler yerine isim soyad gösterebilirim.

    //Set SnackBar
    val toastMessage = userListViewModel.toastMessage.value
    LaunchedEffect(key1 = toastMessage){
        if(toastMessage != ""){
            SnackbarController(this).showSnackbar(snackbarHostState,toastMessage, null)
        }
    }

    //Chat Screen Navigate
    var chatRoomUUID : String? by remember {mutableStateOf(null)}
    var opponentUUID : String? by remember {mutableStateOf(null)}
    var oneSignalUserId : String? by remember {mutableStateOf(null)}
    var registerUUID : String? by remember { mutableStateOf(null)}
    if(chatRoomUUID != null && opponentUUID != null && registerUUID != null && oneSignalUserId != null){
        LaunchedEffect(key1 = Unit){
            navController.popBackStack()
            navController.navigate(BottomNavItem.Chat.screen_route + "/${chatRoomUUID}" + "/${opponentUUID}" + "/${registerUUID}"+ "/${oneSignalUserId}")
        }
    }

    //Get FriendList
    LaunchedEffect(key1 = Unit){
        userListViewModel.refreshFriendList()
    }
    val acceptedFriendRequestList = userListViewModel.acceptedFriendRequestList
    val pendingFriendRequestList = userListViewModel.pendingFriendRequestList

    //Compose Components
    Column(modifier = Modifier
            .background(MaterialTheme.colors.background)
            .focusable()
            .pointerInput(Unit) {
            detectTapGestures(onTap = { keyboardController.hide() })
        }) {

        //Alert Dialog For Add Friend
        var showAlertDialog by remember { mutableStateOf(false) }
        UserListAppBar(onUserAddButtonClick = {
            showAlertDialog = !showAlertDialog
        })
        if (showAlertDialog) {
            AlertDialog(
                onDismiss = {showAlertDialog = !showAlertDialog},
                onConfirm = {
                    showAlertDialog = !showAlertDialog
                    userListViewModel.createFriendshipRegisterToFirebase(it)
                })
        }

        val scrollState = rememberLazyListState()
        var isRefreshing by remember { userListViewModel.isRefreshing}

        //SwipeRefresh LazyColumn
        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = {
                isRefreshing = true
                userListViewModel.refreshFriendList()
            }) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                state = scrollState,
                contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8 .dp)
            ) {
                items(acceptedFriendRequestList.value) { item ->

                    AcceptedFriendRequestList(item) {
                        chatRoomUUID = item.chatRoomUUID
                        registerUUID = item.registerUUID
                        opponentUUID = item.userUUID
                        oneSignalUserId = item.oneSignalUserId
                    }

                }
                items(pendingFriendRequestList.value){ item ->

                    PendingFriendRequestList(item,{
                        userListViewModel.acceptPendingFriendRequestToFirebase(item.registerUUID)
                        userListViewModel.refreshFriendList()
                    },{
                        userListViewModel.cancelPendingFriendRequestToFirebase(item.registerUUID)
                        userListViewModel.refreshFriendList()
                    })
                }
            }
        }
    }
}

