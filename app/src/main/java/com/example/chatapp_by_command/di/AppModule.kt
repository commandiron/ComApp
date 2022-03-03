package com.example.chatapp_by_command.di

import android.app.Application
import android.content.Context
import com.example.chatapp_by_command.core.Constants.ONESIGNAL_APP_ID
import com.example.chatapp_by_command.data.repository.AppRepositoryImpl
import com.example.chatapp_by_command.domain.repository.AppRepository
import com.example.chatapp_by_command.domain.use_case.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.onesignal.OSDeviceState
import com.onesignal.OneSignal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuthInstance() = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseStorageInstance() = FirebaseStorage.getInstance()

    @Provides
    fun provideFirebaseDatabaseInstance() = FirebaseDatabase.getInstance("https://commandchatapp-default-rtdb.europe-west1.firebasedatabase.app/")

    @Provides
    fun provideSharedPreferences(application: Application) = application.getSharedPreferences("login", Context.MODE_PRIVATE)

//    @Singleton
//    @Provides
//    fun provideUserDatabase(@ApplicationContext context: Context) =
//        Room.databaseBuilder(context, AppDatabase::class.java,"userdatabase").fallbackToDestructiveMigration().build()
//
//    @Singleton
//    @Provides
//    fun provideUserDao(db: AppDatabase) = db.appDao()


    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        storage: FirebaseStorage,
        databaseFirebase: FirebaseDatabase
        //appDAO: AppDAO
    ): AppRepository = AppRepositoryImpl(auth, storage, databaseFirebase)

    @Provides
    fun provideUseCases(repository: AppRepository) = UseCases(
        //Login Screen
        isUserAuthenticated = IsUserAuthenticated(repository),
        signIn = SignIn(repository),
        signUp = SignUp(repository),

        //Profile Screen
        signOut = SignOut(repository),
        uploadPictureToFirebase = UploadPictureToFirebase(repository),
        createOrUpdateProfileToFirebase = CreateOrUpdateProfileToFirebase(repository),
        loadProfileFromFirebase = LoadProfileFromFirebase(repository),
        setUserStatusToFirebase = SetUserStatusToFirebase(repository),

        //UserListScreen
        checkChatRoomIsExistFromFirebase = CheckChatRoomIsExistFromFirebase(repository),
        createChatRoomToFirebase = CreateChatRoomToFirebase(repository),
        searchUserFromFirebase = SearchUserFromFirebase(repository),
        checkFriendListRegisterIsExistFromFirebase = CheckFriendListRegisterIsExistFromFirebase(repository),
        createFriendListRegisterToFirebase = CreateFriendListRegisterToFirebase(repository),
        loadPendingFriendRequestListFromFirebase = LoadPendingFriendRequestListFromFirebase(repository),
        loadAcceptedFriendRequestListFromFirebase = LoadAcceptedFriendRequestListFromFirebase(repository),
        acceptedPendingFriendRequestToFirebase = AcceptPendingFriendRequestToFirebase(repository),
        cancelPendingFriendRequestToFirebase = CancelPendingFriendRequestToFirebase(repository),
        blockFriendToFirebase = BlockFriendToFirebase(repository),
        openBlockedFriendToFirebase = OpenBlockedFriendToFirebase(repository),

        //ChatScreen
        insertMessageToFirebase = InsertMessageToFirebase(repository),
        loadMessagesFromFirebase = LoadMessagesFromFirebase(repository),
        loadOpponentProfileFromFirebase = LoadOpponentProfileFromFirebase(repository)
    )
}