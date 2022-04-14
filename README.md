# ChatApp <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" width="80" height="20"> <img src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white" width="80" height="20">

Native Android simple chat application written in Kotlin using Jetpack Compose.

* Video 🧪

|Splash, SignUp, Profile|Conversation|
|-----------------------|------------|
|<img src="https://user-images.githubusercontent.com/50905347/157693357-dbfc95de-627a-4883-bff7-d5847cfafe3b.gif" width="258" height="532">|<img src="https://user-images.githubusercontent.com/50905347/157693963-37351ce3-ebc9-491a-bfed-1eef10cf083b.gif" width="532" height="545">|

* Screenshots 🖼️

|Splash|Login|Profile|
|------|-----|-------|
|<img src="https://user-images.githubusercontent.com/50905347/163385930-1e585b03-cb51-42ba-b4d5-0f2f90a47f4e.png" width="250" height="530">|<img src="https://user-images.githubusercontent.com/50905347/163385935-12b3814f-d2aa-4061-9ea1-c88c18944fec.png" width="250" height="530">|<img src="https://user-images.githubusercontent.com/50905347/163385937-cc5eef02-54fd-45da-be3c-74ca053e093a.png" width="250" height="530">|
|UserList|Chat|Dark Theme|
|<img src="https://user-images.githubusercontent.com/50905347/163385939-56bd24b6-b3f0-439d-840f-612c3fff52e7.png" width="250" height="530">|<img src="https://user-images.githubusercontent.com/50905347/163385940-1018cf5d-c74f-4710-ac50-a57ae830f249.png" width="250" height="530">|<img src="https://user-images.githubusercontent.com/50905347/163385941-b8facff6-b986-4bd4-828b-3cca882682d7.png" width="250" height="530">|

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
