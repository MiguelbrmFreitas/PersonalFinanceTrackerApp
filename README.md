# Solution
That's what the app looks like for now.

<img src="https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/3418d976-cc76-4c94-b41b-7a02b8032d31" width="300">

### Offline cache demonstration

<img src="https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/04531a49-f388-488f-ab36-24bc8cc28a10" width="300">

The required features were developed using the most modern Android development approaches that'll be described below.

# Tech Stack
- Coded in [Kotlin](https://kotlinlang.org)
- Layout and components implemented with [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAjwoqGnBhAcEiwAwK-OkUa-37do3KnzC0PtXZR4Nnh24MwS1_xJfXZmn7vAIPST0DcoSErlpRoCYWYQAvD_BwE&gclsrc=aw.ds)
- State handling with [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) and [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
- Dependency Injection with [Koin](https://insert-koin.io/)
- Concurrency with [Coroutines](https://kotlin.github.io/kotlinx.coroutines/) and [Flow](https://developer.android.com/kotlin/flow)
- Local database persistence with [Room](https://developer.android.com/training/data-storage/room)
- Network calls with [Retrofit](https://github.com/square/retrofit)
- JSON parsing with [Moshi](https://github.com/square/moshi)
- Unit testing with [JUnit4](https://developer.android.com/training/testing/local-tests)
- Mocking objects with [Mockk](https://mockk.io/ANDROID.html)
- Mocking server calls with [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)

# Architecture and project structure

The architecture pattern used is MVVM with Clean Architecture and SOLID principles, always striving for a reusable, readable, maintainable, testable, scalable codebase. 

The high level layers of the app were separated like this:

<img src="https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/31f931dd-62df-430a-b700-b3e38d417679" width="800">

Sometimes it's good to build a **domain** layer with abstractions and interfaces between **data** and **presentation** layers, but for the purpose of this project it wasn't necessary at first. It could easily be implemented later if the app starts growing.

## Project modules
The highest level layers of **presentation** and **data** were implemented as Android modules. This helps keeping separation of concerns and each module has its own dependency tree on Gradle, so it's easier to organize. As the app grows, the modularization should as well.

## Data module
This module is responsible to describe, fetch and handle all data the app needs. It's implemented as an Android Library since it doesn't have any UI. It could even be reused for other platforms with [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html).

![image](https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/083ee057-12cd-44f6-949c-891390a5214c)

### Models
  
![image](https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/98cf4f86-5aac-4da5-99bf-ee69b8efaf9f)

Those are the models that hold all the data our app needs. They're mapped after API calls.

### Repository

![image](https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/6cca7ba1-a988-401c-a094-f72f51d1721e)

Repository is a design pattern that encapsulates access to data. Here we have both remote and local data sources, and it's the repository job to decide which one to use.

API calls are made with Retrofit, JSON parsed with Moshi and offline caching is implemented with Room database (framework on top of SQLite)

- remote: responsible for API calls. Defined /balance and /transactions endpoints
- entity: entities represent the tables in our Room database
- dao: Data Access Objects are interfaces that describes database queries
- database: it's the class that holds the instance to the actual database
- converters: responsible to map non primitive types to the database

### UseCases

![image](https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/31229128-5aa4-4c69-9d27-bd748e9c69cd)

UseCases are called from presentation layer to get data from repository and may add business logic on top of that if necessary. They help keeping things separated and are easy to test.

### Core

![image](https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/d8335785-00f0-4dc4-9f85-3d3ef2d8af9e)

Foundation classes that are reused:
- BaseUseCase: abstract class that describes a UseCase
- CustomResponse: response wrapper to repository requests: state may be Success, Error or Loading. It's used on presentation layer after fetching from usecases
- NetworkBoundResource: helper function used by repository to handle offline caching
- RemoteException: encapsulates exception error from remote server
- Resource: same principle of CustomResponse, but used within data layer

### Unit tests

![image](https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/a3e08484-722a-4289-b70d-35e13c2b8462)

Tested UseCases and API calls (with the static JSON provided being used as mocked response). Wrote helper classes to mock data.


## App module (presentation layer)
That's the entry point of the app and handles everything related to UI/UX.

![image](https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/fa1864d2-67cc-48aa-92e5-b6510ee2302f)

### ViewModel

![image](https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/42e86890-cdb3-407b-a0e0-c281a27e2fc8)


Following the MVVM pattern, here we have a implementation of ViewModel to manage UI state and listen to UseCases results. It take advantage of Kotlin Flows for concurrency and reactive programming.

### View

![image](https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/3f7b17d9-d6e9-4a45-934c-5550ecba92f5)

The UI was implemented with Jetpack Compose. MainActivity is the entry point for composable functions.

The **components** folder has all components written as composable functions, responsible for describing the layout.

Composable functions receive live updates from ViewModel and updates the UI accordingly. It also decides how to draw the components depending on state (Success/Loading/Error).

SplashActivity is a simple screen that loads on app start for 2 seconds.

### Extension functions

![image](https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/18a24195-e2fe-4f9e-b4cb-98badc0c6f6e)

Useful helper functions taking advantage of this amazing Kotlin feature.

## Dependency Injection

Implemented dependency injection for all layers with Koin. 

The dependency are setup on ModuleProviders classes and Koin is initialized at **MainApplication**

![image](https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/6ce807d5-af90-4728-a035-2f5f51783769)
![image](https://github.com/TheoremOne-Assessments/mobile-cc728bd6-6e41-42e2-b25b-d07e5c2ef2e4-mobile-android/assets/6539610/d2fbe5ae-435e-4804-8748-df8610ac4bc5)

## What could improve 
- Implementation of optional in-app billing feature with advices
- Bigger test coverage
- Better organization at Gradle files
- Implementation of pagination feature to get more than the first 10 transactions
- Better use of reusable themes instead of setting styles directly on Compose modifiers

## Instructions


The client's Personal Finance Tracker App is in progress and is at a stage where users can currently view the balance of a specific account. The client has expressed the desire to add further functionality to enhance the user experience, and here's a breakdown of their requests:

1. Below the account balance, we would like to see the last ten transactions, including the title and cost (this account will only contain debits, no credits).
2. The account balance and transactions should be saved so that the user can view their balance and transactions even when offline. These values should refresh when a new network connection is available.
3. Implement the new styles that have been provided in a [Figma file](https://www.figma.com/file/gc7NONoPrghg2sVwItLu6f/Formula-Money?type=design&node-id=1%3A2&mode=design&t=jayHJnsOxRog2r49-1).

**Stretch Goals (Optional):**
- **In-App Purchase for Premium Feature:** Implement in-app billing functionality to manage access to our premium tier. We have an API that can take a collection of transactions and display simple budgeting advice for the user.

These requests come directly from the client alongside the initial codebase. The stretch goals are optional but would be highly valuable if achievable within the time frame.

The client has also provided an [API service](https://8kq890lk50.execute-api.us-east-1.amazonaws.com/prd/api) you can use for retrieving transactional data. It's already used for the Balance. If, for any reason, the API service is down, you can use the JSON files, provided in the [data](/data/json/) folder.

Please review the existing assets and begin working on these features as per the client's requests.
