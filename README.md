# ChatApp <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" width="80" height="20"> <img src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white" width="80" height="20">

Native Android simple chat application written in Kotlin using Jetpack Compose.

* Video 🧪

|Splash, SignUp, Profile|Conversation|
|-----------------------|------------|
|<img src="https://user-images.githubusercontent.com/50905347/157693357-dbfc95de-627a-4883-bff7-d5847cfafe3b.gif" width="258" height="532">|<img src="https://user-images.githubusercontent.com/50905347/157693963-37351ce3-ebc9-491a-bfed-1eef10cf083b.gif" width="532" height="545">|

* Screenshots 🖼️

|Splash|Login|Profile|
|------|-----|-------|
|<img src="https://user-images.githubusercontent.com/50905347/163364316-643a02c7-c9ec-49b5-9e1e-5208a70647ed.png" width="250" height="530">|<img src="https://user-images.githubusercontent.com/50905347/163364324-a8ff0113-4e81-4297-a7f2-61030a9e9282.png" width="250" height="530">|<img src="https://user-images.githubusercontent.com/50905347/163374547-fc4e4a83-d2ca-4a84-8554-b60888e3d1fd.png" width="250" height="530">|
|UserList|Chat|Dark Theme|
|<img src="https://user-images.githubusercontent.com/50905347/163364339-b1899ed4-5e94-45d4-8927-817466e86855.png" width="295" height="625">|<img src="https://user-images.githubusercontent.com/50905347/163364343-55913181-7bff-4c42-baa8-8f8ad116a840.png" width="295" height="625">|<img src="https://user-images.githubusercontent.com/50905347/163364348-09990183-da90-4d85-9ae5-fb8b1eb09f9f.png" width="295" height="625">|

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
