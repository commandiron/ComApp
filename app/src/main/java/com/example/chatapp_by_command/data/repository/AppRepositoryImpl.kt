package com.example.chatapp_by_command.data.repository

import android.net.Uri
import com.example.chatapp_by_command.core.Constants.ERROR_MESSAGE
import com.example.chatapp_by_command.core.Constants.NO_CHATROOM_IN_FIREBASE_DATABASE
import com.example.chatapp_by_command.domain.model.*
import com.example.chatapp_by_command.domain.model.Response.*
import com.example.chatapp_by_command.domain.model.enumclasses.FriendStatus
import com.example.chatapp_by_command.domain.model.enumclasses.UserStatus
import com.example.chatapp_by_command.domain.repository.AppRepository
import com.example.chatapp_by_command.domain.model.enumclasses.MessageStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.onesignal.OneSignal
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.HashMap


@Singleton
class AppRepositoryImpl  @Inject constructor(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val databaseFirebase: FirebaseDatabase
): AppRepository {

    //Login Screen

    override fun isUserAuthenticatedInFirebase(): Flow<Response<Boolean>> = flow{

        try {
            emit(Loading)
            if(auth.currentUser != null){
                emit(Success(true))
            }else{
                emit(Success(false))
            }

        } catch (e: Exception) {
            emit(Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun signIn(email: String, password: String): Flow<Response<Boolean>> = callbackFlow {

        try {
            this@callbackFlow.trySendBlocking(Loading)
            auth.signInWithEmailAndPassword(email,password).apply {
                this.await()
                if(this.isSuccessful){
                    this@callbackFlow.trySendBlocking(Success(true))
                }else{
                    this@callbackFlow.trySendBlocking(Success(false))
                }
            }
        } catch (e: Exception) {
            this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
        }

        awaitClose {
            channel.close()
            cancel()
        }
    }

    override suspend fun signUp(email: String, password: String): Flow<Response<Boolean>> = callbackFlow {
        try {
            this@callbackFlow.trySendBlocking(Loading)
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                if(it.user != null){
                    this@callbackFlow.trySendBlocking(Success(true))
                }
            }.addOnFailureListener {
                this@callbackFlow.trySendBlocking(Error(it.message ?: ERROR_MESSAGE))
            }
        } catch (e: Exception) {
            this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }

    //Profile Screen

    override suspend fun signOut(): Flow<Response<Boolean>> = callbackFlow {

        try {
            this@callbackFlow.trySendBlocking(Loading)
            auth.signOut().apply {
                this@callbackFlow.trySendBlocking(Success(true))
            }
        } catch (e: Exception) {
            this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
        }

        awaitClose {
            channel.close()
            cancel()
        }
    }

    override suspend fun uploadPictureToFirebase(uri: Uri): Flow<Response<String>> = flow {
        try {
            emit(Loading)
            val uuidImage = UUID.randomUUID()
            val imageName = "images/$uuidImage.jpg"
            val storageRef = storage.reference.child(imageName)

            storageRef.putFile(uri).apply{}.await()
            var downloadUrl = ""
            storageRef.downloadUrl.addOnSuccessListener {
                downloadUrl = it.toString()
            }.await()
            emit(Success(downloadUrl))


        } catch (e: Exception) {
            emit(Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun createOrUpdateProfileToFirebase(
        myUser: MyUser
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Loading)

            val userUUID = auth.currentUser?.uid.toString()
            val userEmail = auth.currentUser?.email.toString()
            val oneSignalUserId = OneSignal.getDeviceState()?.userId.toString()

            val databaseReference = databaseFirebase.getReference("Profiles").child(userUUID).child("profile")

            val childUpdates = mutableMapOf<String,Any>()

            childUpdates.put("/profileUUID/", userUUID)
            childUpdates.put("/userEmail/", userEmail)
            childUpdates.put("/oneSignalUserId/", oneSignalUserId)
            if(myUser.userName!= "") childUpdates.put("/userName/",myUser.userName)
            if(myUser.userProfilePictureUrl!= "") childUpdates.put("/userProfilePictureUrl/",myUser.userProfilePictureUrl)
            if(myUser.userSurName!="") childUpdates.put("/userSurName/",myUser.userSurName)
            if(myUser.userBio!="") childUpdates.put("/userBio/",myUser.userBio)
            if(myUser.userPhoneNumber!="") childUpdates.put("/userPhoneNumber/",myUser.userPhoneNumber)
            childUpdates.put("/status/", UserStatus.ONLINE.toString())

            databaseReference.updateChildren(childUpdates).await()
            emit(Success(true))


        }catch (e: Exception){
            emit(Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun loadProfileFromFirebase(): Flow<Response<MyUser>> = callbackFlow {
        try {

            this@callbackFlow.trySendBlocking(Loading)

            val userUUID = auth.currentUser?.uid

            val databaseReference = databaseFirebase.getReference("Profiles")

            val postListener = databaseReference.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val userFromFirebaseDatabase = snapshot.child(userUUID!!).child("profile").getValue(MyUser::class.java) ?: MyUser()
                    this@callbackFlow.trySendBlocking(Success(userFromFirebaseDatabase))
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySendBlocking(Error(error.message))
                }
            })

            databaseReference.addValueEventListener(postListener)
            awaitClose {
                databaseReference.removeEventListener(postListener)
                channel.close()
                cancel()
            }
        } catch (e: Exception){
            this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun setUserStatusToFirebase(userStatus: UserStatus): Flow<Response<Boolean>> = flow {
        try {
            emit(Loading)

            if(auth.currentUser != null){
                val userUUID = auth.currentUser?.uid.toString()

                val databaseReference = databaseFirebase.getReference("Profiles").child(userUUID).child("profile").child("status")
                databaseReference.setValue(userStatus.toString()).await()

                emit(Success(true))
            }else{
                emit(Success(false))
            }

        }catch (e: Exception){
            emit(Error(e.message ?: ERROR_MESSAGE))
        }
    }

    //UserListScreen

    override suspend fun loadAcceptedFriendRequestListFromFirebase(): Flow<Response<List<FriendListUiRow>>> = callbackFlow {
        try {
            val myUUID = auth.currentUser?.uid
            val databaseReference = databaseFirebase.getReference("Friend_List")

            databaseReference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    var friendListRegisterAcceptedList = listOf<FriendListRegister>()
                    launch {
                        for(i in snapshot.children){
                            val friendListRegister = i.getValue(FriendListRegister::class.java)

                            if(friendListRegister?.status == FriendStatus.ACCEPTED.toString()){
                                friendListRegisterAcceptedList += friendListRegister
                            }

                        }

                        launch {

                            var friendListUiRowList = listOf<FriendListUiRow>()

                            for(i in friendListRegisterAcceptedList){
                                val pictureUrl: String = ""
                                val chatRoomUUID: String = i.chatRoomUUID
                                val registerUUID: String = i.registerUUID
                                val lastMessage: ChatMessage = i.lastMessage

                                var email: String = ""
                                var uuid: String = ""
                                var oneSignalUserId: String = ""

                                if(i.requesterUUID == myUUID){
                                    email = i.acceptorEmail
                                    uuid = i.acceptorUUID
                                    oneSignalUserId = i.acceptorOneSignalUserId
                                }else if(i.acceptorUUID == myUUID){
                                    email = i.requesterEmail
                                    uuid = i.requesterUUID
                                    oneSignalUserId = i.requesterOneSignalUserId
                                }

                                if(email != "" && uuid != ""){
                                    val friendListUiRow = FriendListUiRow(chatRoomUUID, email, uuid, oneSignalUserId, registerUUID, pictureUrl, lastMessage)
                                    friendListUiRowList += friendListUiRow
                                }
                            }
                            launch {

                                var resultList = listOf<FriendListUiRow>()

                                val asyncTask = async {

                                    for(i in friendListUiRowList){

                                        databaseFirebase
                                            .getReference("Profiles")
                                            .child(i.userUUID)
                                            .child("profile")
                                            .child("userProfilePictureUrl")
                                            .get().addOnSuccessListener {

                                                val friendListUiRow = FriendListUiRow(i.chatRoomUUID,i.userEmail,i.userUUID,i.oneSignalUserId,i.registerUUID,if(it.value != null)it.value as String else "",i.lastMessage)
                                                resultList += friendListUiRow

                                            }.addOnFailureListener {
                                                this@callbackFlow.trySendBlocking(Error(it.localizedMessage ?: ERROR_MESSAGE))
                                            }
                                    }

                                    delay(1000)
                                }

                                asyncTask.invokeOnCompletion {
                                    this@callbackFlow.trySendBlocking(Success(resultList))
                                }
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySendBlocking(Error(error.message))
                }
            })

            awaitClose {
                channel.close()
                cancel()
            }

        }catch (e: Exception){
            this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun loadPendingFriendRequestListFromFirebase(): Flow<Response<List<FriendListRegister>>> = callbackFlow {

        val myUUID = auth.currentUser?.uid

        val databaseReference = databaseFirebase.getReference("Friend_List")

        val postListener = databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    var resultList = listOf<FriendListRegister>()
                    for(i in snapshot.children){

                        val friendListRegister = i.getValue(FriendListRegister::class.java)
                        if(friendListRegister?.status == FriendStatus.PENDING.toString() && friendListRegister.acceptorUUID == myUUID){
                            resultList = resultList + friendListRegister
                        }
                    }
                    this@callbackFlow.trySendBlocking(Success(resultList))

                }catch (e: Exception){
                    this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Error(error.message))
            }
        })

        databaseReference.addValueEventListener(postListener)
        awaitClose {
            databaseReference.removeEventListener(postListener)
            channel.close()
            cancel()
        }
    }

    override suspend fun searchUserFromFirebase(userEmail: String): Flow<Response<MyUser?>> = callbackFlow{
        try {

            this@callbackFlow.trySendBlocking(Loading)

            val databaseReference = databaseFirebase.getReference("Profiles")

            var user: MyUser?

            databaseReference.get().addOnSuccessListener {
                var flagForControl = false

                val myJob = launch {
                    for(i in it.children){
                        user = i.child("profile").getValue(MyUser::class.java)!!
                        if(user?.userEmail == userEmail){
                            flagForControl=true
                            this@callbackFlow.trySendBlocking(Success(user))
                        }
                    }
                }

                myJob.invokeOnCompletion {
                    if(!flagForControl){
                        this@callbackFlow.trySendBlocking(Success(null))
                    }
                }

            }.addOnFailureListener {
                this@callbackFlow.trySendBlocking(Error(it.message ?: ERROR_MESSAGE))
            }

            awaitClose {
                channel.close()
                cancel()
            }

        } catch (e: Exception){
            this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun checkChatRoomIsExistFromFirebase(acceptorUUID: String): Flow<Response<String>> = callbackFlow<Response<String>> {
        try {
            this@callbackFlow.trySendBlocking(Loading)

            val requesterUUID = auth.currentUser?.uid

            val hashMapOfRequesterUUIDAndAcceptorUUID = hashMapOf<String, String>()
            hashMapOfRequesterUUIDAndAcceptorUUID.put(requesterUUID!!,acceptorUUID)

            val hashMapOfAcceptorUUIDAndRequesterUUID = hashMapOf<String, String>()
            hashMapOfAcceptorUUIDAndRequesterUUID.put(acceptorUUID,requesterUUID)

            val gson = Gson()
            val requesterUUIDAndAcceptorUUID = gson.toJson(hashMapOfRequesterUUIDAndAcceptorUUID)
            val acceptorUUIDAndRequesterUUID = gson.toJson(hashMapOfAcceptorUUIDAndRequesterUUID)

            val databaseReference = databaseFirebase.getReference("Chat_Rooms")

            databaseReference.get().addOnSuccessListener {
                try {
                    var keyListForControl = listOf<String>()
                    val hashMapForControl = hashMapOf<String,Any>()
                    for(i in it.children){
                        val key = i.key as String
                        keyListForControl = keyListForControl + key
                        val hashMap: Map<String, Any> = Gson().fromJson(i.key, object : TypeToken<HashMap<String?, Any?>?>() {}.type)

                        hashMapForControl.putAll(hashMap)
                    }

                    var chatRoomUUIDString: String?

                    if(keyListForControl.contains(requesterUUIDAndAcceptorUUID)){

                        //ChatRoom opened by Requester
                        val hashMapOfRequesterUUIDAndAcceptorUUIDForSaveMessagesToFirebase = hashMapOf<String, Any>()
                        hashMapOfRequesterUUIDAndAcceptorUUIDForSaveMessagesToFirebase.put(requesterUUID, acceptorUUID)

                        val gson = Gson()
                        chatRoomUUIDString = gson.toJson(hashMapOfRequesterUUIDAndAcceptorUUIDForSaveMessagesToFirebase)

                        this@callbackFlow.trySendBlocking(Success(chatRoomUUIDString!!))

                    }else if(keyListForControl.contains(acceptorUUIDAndRequesterUUID)){

                        //ChatRoom opened by Acceptor
                        val hashMapOfAcceptorUUIDAndRequesterUUIDForSaveMessagesToFirebase = hashMapOf<String, Any>()
                        hashMapOfAcceptorUUIDAndRequesterUUIDForSaveMessagesToFirebase.put(acceptorUUID, requesterUUID)

                        val gson = Gson()
                        chatRoomUUIDString = gson.toJson(hashMapOfAcceptorUUIDAndRequesterUUIDForSaveMessagesToFirebase)

                        this@callbackFlow.trySendBlocking(Success(chatRoomUUIDString!!))

                    }else{
                        this@callbackFlow.trySendBlocking(Success(NO_CHATROOM_IN_FIREBASE_DATABASE))
                    }
                }catch (e: JsonSyntaxException){
                    this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
                }
            }

            awaitClose {
                channel.close()
                cancel()
            }

        }catch (e: Exception){
            this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun createChatRoomToFirebase(acceptorUUID: String):
            Flow<Response<String>> = flow {

        try {
            emit(Loading)

            val requesterUUID = auth.currentUser?.uid

            val hashMapOfRequesterUUIDAndAcceptorUUID = hashMapOf<String, String>()
            hashMapOfRequesterUUIDAndAcceptorUUID.put(requesterUUID!!,acceptorUUID)

            val databaseReference = databaseFirebase.getReference("Chat_Rooms")

            val gson = Gson()
            val requesterUUIDAndAcceptorUUID = gson.toJson(hashMapOfRequesterUUIDAndAcceptorUUID)

            databaseReference
                .child(requesterUUIDAndAcceptorUUID)
                .setValue(true)
                .await()

            emit(Success(requesterUUIDAndAcceptorUUID))

        }catch (e: Exception){
            emit(Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun checkFriendListRegisterIsExistFromFirebase(
        acceptorEmail: String,
        acceptorUUID: String
    ): Flow<Response<FriendListRegister>> = callbackFlow{

        try {
            this@callbackFlow.trySendBlocking(Loading)
            val requesterUUID = auth.currentUser?.uid
            val databaseReference = databaseFirebase.getReference("Friend_List")

            databaseReference.get().addOnSuccessListener {
                var result = FriendListRegister()

                val job = launch {
                    for(i in it.children){
                        val friendListRegister = i.getValue(FriendListRegister::class.java)
                        if(friendListRegister?.requesterUUID == requesterUUID && friendListRegister?.acceptorUUID == acceptorUUID){
                            result = friendListRegister
                        }else if(friendListRegister?.requesterUUID == acceptorUUID && friendListRegister.acceptorUUID == requesterUUID){
                            result = friendListRegister
                        }
                    }
                }

                job.invokeOnCompletion {
                    this@callbackFlow.trySendBlocking(Success(result))
                }
            }

            awaitClose {
                channel.close()
                cancel()
            }

        }catch (e: Exception){
            this@callbackFlow.trySendBlocking(Error(e.message ?: "Error Message"))
        }
    }

    override suspend fun createFriendListRegisterToFirebase(
        chatRoomUUID: String,
        acceptorEmail: String,
        acceptorUUID: String,
        acceptorOneSignalUserId: String
    ): Flow<Response<Boolean>> = flow {

        try {
            emit(Loading)

            val registerUUID = UUID.randomUUID().toString()

            val requesterEmail = auth.currentUser?.email
            val requesterUUID = auth.currentUser?.uid
            val requesterOneSignalUserId = OneSignal.getDeviceState()?.userId

            val databaseReference = databaseFirebase.getReference("Friend_List")

            val friendListRegister =
                FriendListRegister(
                    chatRoomUUID,
                    registerUUID,
                    requesterEmail!!,
                    requesterUUID!!,
                    requesterOneSignalUserId!!,
                    acceptorEmail,
                    acceptorUUID,
                    acceptorOneSignalUserId,
                    FriendStatus.PENDING.toString(),
                    ChatMessage()
                )

            databaseReference
                .child(registerUUID)
                .setValue(friendListRegister)
                .await()

            emit(Success(true))

        }catch (e: Exception){
            emit(Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun acceptPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>> = callbackFlow {

        try {
            this@callbackFlow.trySendBlocking(Loading)

            val databaseReference = databaseFirebase.getReference("Friend_List").child(registerUUID)

            val childUpdates = mutableMapOf<String,Any>()
            childUpdates.put("/status/", FriendStatus.ACCEPTED.toString())

            databaseReference.updateChildren(childUpdates).addOnSuccessListener {
                this@callbackFlow.trySendBlocking(Success(true))
            }.addOnFailureListener {
                this@callbackFlow.trySendBlocking(Success(false))
            }

        }catch (e: Exception){
            this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
        }

        awaitClose {
            channel.close()
            cancel()
        }
    }

    override suspend fun cancelPendingFriendRequestToFirebase(registerUUID: String): Flow<Response<Boolean>> = flow{
        try {

            emit(Loading)
            databaseFirebase.getReference("Friend_List").child(registerUUID).setValue(null).await()
            emit(Success(true))

        }catch (e: Exception) {
            emit(Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun openBlockedFriendToFirebase(registerUUID: String): Flow<Response<Boolean>> = callbackFlow{
        try {
            this@callbackFlow.trySendBlocking(Loading)

            val myUUID = auth.currentUser?.uid

            val databaseReference = databaseFirebase.getReference("Friend_List").child(registerUUID)


            databaseReference.get().addOnSuccessListener {

                val result = it.getValue() as Map<String, Any>

                if(result["blockedby"] == myUUID){
                    val childUpdates = mutableMapOf<String,Any?>()
                    childUpdates.put("/status/", FriendStatus.ACCEPTED.toString())
                    childUpdates.put("/blockedby/", null)

                    databaseReference.updateChildren(childUpdates)

                    this@callbackFlow.trySendBlocking(Success(true))
                }else{
                    this@callbackFlow.trySendBlocking(Success(false))
                }
            }
        }catch (e: Exception){
            this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
        }

        awaitClose {
            channel.close()
            cancel()
        }
    }

    //ChatScreen

    override suspend fun insertMessageToFirebase(
        chatRoomUUID: String,
        messageContent: String,
        registerUUID: String,
        oneSignalUserId : String): Flow<Response<Boolean>> = flow {
        try {
            emit(Loading)

            val myUUID = auth.currentUser?.uid
            val myEmail = auth.currentUser?.email
            val messageUUID = UUID.randomUUID().toString()

            OneSignal.postNotification(JSONObject("{'contents': {'en':'$myEmail: $messageContent'}, 'include_player_ids': ['" + oneSignalUserId + "']}"), object: OneSignal.PostNotificationResponseHandler{
                override fun onSuccess(p0: JSONObject?) {
                    println("onSuccess")
                }
                override fun onFailure(p0: JSONObject?) {
                    println("onFailure: " + p0.toString())
                }
            })

            val message = ChatMessage(
                myUUID!!,
                messageContent,
                System.currentTimeMillis(),
                MessageStatus.RECEIVED.toString()
            )

            val databaseRefForLastMessage =
                databaseFirebase.reference.child("Friend_List").child(registerUUID).child("lastMessage")
            databaseRefForLastMessage.setValue(message).await() //For Last Message

            val databaseRefForChatMessage = databaseFirebase.reference.child("Chat_Rooms").child(chatRoomUUID).child(messageUUID)
            databaseRefForChatMessage.setValue(message).await() //For InsertMessage

            emit(Success(true))

        } catch (e: Exception) {
            emit(Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun loadMessagesFromFirebase(
        chatRoomUUID: String,
        opponentUUID: String,
        registerUUID: String
    ): Flow<Response<List<ChatMessage>>> = callbackFlow {

        try {
            this@callbackFlow.trySendBlocking(Loading)

            val myUUID = auth.currentUser?.uid

            val databaseRefForMessageStatus =
            databaseFirebase.getReference("Friend_List").child(registerUUID).child("lastMessage")
            val lastMessageProfileUUID = databaseRefForMessageStatus.child("profileUUID").get().await().value as String

            if(lastMessageProfileUUID != myUUID){
                databaseRefForMessageStatus.child("status").setValue(MessageStatus.READ.toString())
            }

            val databaseRefForLoadMessages = databaseFirebase.getReference("Chat_Rooms").child(chatRoomUUID)

            //Gelen mesajdan dolayı ondatachange tetiklenmiyor.
            val postListener = databaseRefForLoadMessages.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val messageList = arrayListOf<ChatMessage>()

                    var unReadMessageKeys = listOf<String>()

                    val job2 = launch {
                        snapshot.children.forEach {
                            if(it.value?.javaClass != Boolean::class.java){
                                val chatMessage = it.getValue(ChatMessage::class.java)
                                if(chatMessage != null){
                                    messageList.add(chatMessage)

                                    if(chatMessage.profileUUID != myUUID && chatMessage.status == MessageStatus.RECEIVED.toString()){
                                        unReadMessageKeys = unReadMessageKeys + it.key.toString()
                                    }
                                }
                            }
                        }

                        messageList.sortBy { it.date }

                        this@callbackFlow.trySendBlocking(Success(messageList))
                    }

                    job2.invokeOnCompletion {
                        for(i in unReadMessageKeys){
                            databaseRefForLoadMessages.child(i).updateChildren(mapOf(Pair("/status/",
                                MessageStatus.READ)))
                        }
                    }

                    messageList.clear()
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySendBlocking(Error(error.message))
                }
            })

            databaseRefForLoadMessages.addValueEventListener(postListener)

            awaitClose {
                databaseRefForLoadMessages.removeEventListener(postListener)
                channel.close()
                cancel()
            }

        }catch (e: Exception){
            this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun loadOpponentProfileFromFirebase(opponentUUID: String): Flow<Response<MyUser>> = callbackFlow{
        try {

            this@callbackFlow.trySendBlocking(Loading)

            val databaseReference = databaseFirebase.getReference("Profiles").child(opponentUUID).child("profile")

            databaseReference.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(MyUser::class.java)
                    this@callbackFlow.trySendBlocking(Success(user!!))
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySendBlocking(Error(error.message))
                }
            })
        } catch (e: Exception){
            this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
        }

        awaitClose {
            channel.close()
            cancel()
        }
    }

    override suspend fun blockFriendToFirebase(registerUUID: String): Flow<Response<Boolean>> = callbackFlow {

        try {
            this@callbackFlow.trySendBlocking(Loading)

            val myUUID = auth.currentUser?.uid

            val databaseReference = databaseFirebase.getReference("Friend_List").child(registerUUID)

            val childUpdates = mutableMapOf<String,Any>()
            childUpdates.put("/status/", FriendStatus.BLOCKED.toString())
            childUpdates.put("/blockedby/", myUUID.toString())

            databaseReference.updateChildren(childUpdates).await()

            this@callbackFlow.trySendBlocking(Success(true))

        }catch (e: Exception){
            this@callbackFlow.trySendBlocking(Error(e.message ?: ERROR_MESSAGE))
        }

        awaitClose {
            channel.close()
            cancel()
        }
    }

}