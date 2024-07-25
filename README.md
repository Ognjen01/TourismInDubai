# Tourism in Dubai App

**Overview**

This project is a tourism application designed to provide information about various attractions and activities in Dubai. The app is built using modern Android development practices and libraries, ensuring high performance, scalability, and maintainability.


**Features**

Reusable Components: The app is built with Jetpack Compose, leveraging its capabilities to create highly reusable UI components. This ensures reducing the amount of boilerplate code.

Remote Data Communication: Retrofit is used for communication with remote resources. It handles all the network operations, allowing the app to fetch data from APIs efficiently and with minimal code.

Local Caching: Room database is used for caching data locally. This ensures that users have a seamless experience even when they are offline or have poor network connectivity.

Dependency Injection: Dagger Hilt is used for dependency injection. This simplifies the process of managing dependencies and promotes a modular and testable architecture.

Git Flow: The development process followed the Git Flow methodology, ensuring a structured and organized workflow. This includes feature branches, release branches, and hotfixes, which help in maintaining code quality and managing releases effectively.


**Architecture**

The app follows the MVVM (Model-View-ViewModel) architecture, which helps in separating the business logic from the UI and makes the codebase more manageable.

Model: Represents the data layer of the app, including Retrofit interfaces and Room entities.

View: Built using Jetpack Compose, this layer contains all the UI components.

ViewModel: Acts as a bridge between the Model and the View, holding UI-related data and handling the business logic.


**Libraries Used**

Jetpack Compose: For building the UI.

Retrofit: For network operations.

Room: For local data caching.

Dagger Hilt: For dependency injection.

Kotlin Coroutines: For asynchronous programming.

Flow: For handling streams of data asynchronously.

**Github Project**: https://github.com/users/Ognjen01/projects/4

https://github.com/user-attachments/assets/fccbe904-6869-47ba-95bd-1bc82f94e439
