# ComApp

Native Android simple chat application written in Kotlin using Jetpack Compose

* Video 🧪

|Splash, SignUp Profile|Conversation|
|----------------------|------------|
|<img src="https://user-images.githubusercontent.com/50905347/157693357-dbfc95de-627a-4883-bff7-d5847cfafe3b.gif" width="258" height="532">|<img src="https://user-images.githubusercontent.com/50905347/157693963-37351ce3-ebc9-491a-bfed-1eef10cf083b.gif" width="532" height="545">|

* Screenshots 🖼️

|Splash|Login|Profile|
|------|-----|-------|
|<img src="https://user-images.githubusercontent.com/50905347/155622233-f76ff3d7-da3d-47e9-89a2-e401bd0887b7.png" width="250" height="500">|<img src="https://user-images.githubusercontent.com/50905347/155622238-9d075029-19e7-4fb3-a77d-71ba996d41f1.png" width="250" height="500">|<img src="https://user-images.githubusercontent.com/50905347/156538780-ec109ff4-050e-4100-9c35-e084841dd70d.png" width="250" height="500">|
|UserList|Chat|Dark Theme|
|<img src="https://user-images.githubusercontent.com/50905347/156539273-c63cc514-58d4-4b42-89ac-8ecd2f77412d.png" width="250" height="500">|<img src="https://user-images.githubusercontent.com/50905347/156539411-ef3bbeaa-9ddd-4278-a2fc-88f437b00c56.png" width="250" height="500">|<img src="https://user-images.githubusercontent.com/50905347/156539502-cb1155b5-d630-4c2b-94d7-13536dbcbd91.png" width="250" height="500">|

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
    * [One Signal](https://onesignal.com)
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


