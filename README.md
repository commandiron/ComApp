# ChatApp <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" width="80" height="20"> <img src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white" width="80" height="20">

Native Android simple chat application written in Kotlin using Jetpack Compose.

* Screenshots 🖼️

<img src="https://user-images.githubusercontent.com/50905347/163409471-5c03b069-6c41-444c-879a-943295334425.png" width="250" height="530">&nbsp;&nbsp;<img src="https://user-images.githubusercontent.com/50905347/163409478-a24ffb25-3a60-4bbf-80cd-bb2a90ce3f20.png" width="250" height="530">&nbsp;&nbsp;<img src="https://user-images.githubusercontent.com/50905347/163409481-afba6ab5-1522-4d35-a1a3-543df762fb27.png" width="250" height="530">

<img src="https://user-images.githubusercontent.com/50905347/163409482-bf4e1a6c-b731-4ba6-86a0-98106606ab48.png" width="250" height="530">&nbsp;&nbsp;<img src="https://user-images.githubusercontent.com/50905347/163409484-74a6d12d-33e7-4b07-97fb-2aea05e8077b.png" width="250" height="530">&nbsp;&nbsp;<img src="https://user-images.githubusercontent.com/50905347/163409486-b34c264c-87bb-45c5-bbcd-3cc28d666c2d.png" width="250" height="530">

* Video 🧪

|Splash, Login, SignUp|Profile, Add Friend|User List, Chat|
|---------------------|-------------------|---------------|
|<img src="https://user-images.githubusercontent.com/50905347/163409001-37e9c78e-6151-4c6d-bf2b-20fa11195d56.gif" width="250" height="530">|<img src="https://user-images.githubusercontent.com/50905347/163409026-e1ce29d5-f6c0-4e39-8277-a1169d82768c.gif" width="250" height="530">|<img src="https://user-images.githubusercontent.com/50905347/163409040-f85a1ec9-c286-4c40-a6c3-a52de1e0049e.gif" width="250" height="530">|

|Conversation|
|------------|
|<img src="https://user-images.githubusercontent.com/50905347/163415160-e4f3738a-db38-4a28-9c0c-c4ad28850ae7.gif" width="500" height="530">|

* Structure 🌲

|Firebase - Data Structure|App Package Structure|
|-------------------------|---------------------|
|<img src="https://user-images.githubusercontent.com/50905347/156565001-46ee1ac2-f231-47aa-b802-b49a1abed092.png" width="250" height="125">|<img src="https://user-images.githubusercontent.com/50905347/156565459-d8ab03a3-1248-4240-b093-231cff3a24fd.png" width="250" height="125">|

* Application - Features ☕
   * User Status (ONLINE - OFFLINE)
   * Message Status (RECEIVED - READ) - PENDING: After push notification.
   * Friend Status (PENDING, ACCEPTED, CANCELED, BLOCKED)
   * New Message Alert
   * Navigation Animation Between Sceens
   * LazyColumn Last Message Snap
   * UserList Swipe Reflesh
   * Push Notification
   * Dark Theme
   * Language - English

* Tech-stack ⚛️
    * [Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operation
    * [Google Firebase](https://firebase.google.com) - with real time database.
    * [One Signal](https://onesignal.com) - notification.
    * [Flows](https://developer.android.com/kotlin/flow)
    * [Hilt](https://github.com/google/dagger) - DI
    * [Accompanist](https://github.com/google/accompanist)
    * [Jetpack](https://developer.android.com/jetpack)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - deal with whole in-app navigation      
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
        * [Compose](https://developer.android.com/jetpack/compose)
* Architecture 🏗️
    * Model-View-ViewModel
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))
 
 * Todo ✔️
   * Firebase requests code optimization & cleaning ❌
   * Dark theme ✔️
   * Push notification ✔️ Listening only offline & chat navigation when notf. pressed ❌
   * Login via Google ❌
   * Swipe - Gestures ❌
   * Share - Location, Image ❌
   * Group chat ❌
   * Language - Turkish ❌
