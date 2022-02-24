package com.example.chatapp_by_command.domain.use_case

data class UseCases (
    //Login Screen
    val isUserAuthenticated: IsUserAuthenticated,
    val signIn: SignIn,
    val signUp: SignUp,

    //Profile Screen
    val signOut: SignOut,
    val uploadPictureToFirebase: UploadPictureToFirebase,
    val createOrUpdateProfileToFirebase: CreateOrUpdateProfileToFirebase,
    val loadProfileFromFirebase: LoadProfileFromFirebase,
    val setUserStatusToFirebase: SetUserStatusToFirebase,

    //UserList Screen
    val checkChatRoomIsExistFromFirebase: CheckChatRoomIsExistFromFirebase,
    val createChatRoomToFirebase: CreateChatRoomToFirebase,
    val searchUserFromFirebase: SearchUserFromFirebase,
    val checkFriendListRegisterIsExistFromFirebase: CheckFriendListRegisterIsExistFromFirebase,
    val createFriendListRegisterToFirebase: CreateFriendListRegisterToFirebase,
    val loadPendingFriendRequestListFromFirebase: LoadPendingFriendRequestListFromFirebase,
    val loadAcceptedFriendRequestListFromFirebase: LoadAcceptedFriendRequestListFromFirebase,
    val acceptedPendingFriendRequestToFirebase: AcceptPendingFriendRequestToFirebase,
    val cancelPendingFriendRequestToFirebase: CancelPendingFriendRequestToFirebase,
    val openBlockedFriendToFirebase: OpenBlockedFriendToFirebase,

    //ChatScreen
    val insertMessageToFirebase: InsertMessageToFirebase,
    val loadMessagesFromFirebase: LoadMessagesFromFirebase,
    val loadOpponentProfileFromFirebase: LoadOpponentProfileFromFirebase,
    val blockFriendToFirebase: BlockFriendToFirebase
)