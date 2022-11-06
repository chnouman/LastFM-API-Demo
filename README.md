# LastFM API Demo App

#About

Demo Application demonstrating usage of few of the APIs of Last.fm with MVVM and Clean Architecture. The implementation is completed by using the latest Android libraries and 
tried to make sure the test is segregated, testable and easily modifiable.


# Requirements to run the code:

**Pre-Requisite Installations**

- Java development kit(JDK) 11
- Android Studio Dolphin | 2021.3.1 Patch 1 + SDK installation
- Min API Level: 21
- Gradle version: 7.4
- TargetSdkVersion: 32


# How to use
Get latest version of Android studio and clone the repository. Further the below screenshots will be helpful for you folks.
<p float="left">
	<img src="https://raw.githubusercontent.com/chnouman/LastFM-API-Demo/master/art/1.png" width="250px" />
	<img src="https://raw.githubusercontent.com/chnouman/LastFM-API-Demo/master/art/2.png" width="250px" />
	<img src="https://raw.githubusercontent.com/chnouman/LastFM-API-Demo/master/art/3.png" width="250px" />
</p>
<p float="left">
	<img src="https://raw.githubusercontent.com/chnouman/LastFM-API-Demo/master/art/4.png" width="250px" />
	<img src="https://raw.githubusercontent.com/chnouman/LastFM-API-Demo/master/art/5.png" width="250px" />
	<img src="https://raw.githubusercontent.com/chnouman/LastFM-API-Demo/master/art/6.png" width="250px" />
</p>

# Technologies stack(Features):

- Android MVVM (ModelView-View-Model) with Clean Architecture
- Programing Language: Kotlin
- Architectural Navigation Component
- Paging 3 Library for pagination
- Material them (Day(Light) & Night(Dark) support)
- Network library: Retrofit + logging Interceptors
- Kotlin Coroutines & Coroutine+live data builders
- Android View Binding
- Hilt for dependency Injection
- Leak canary added for memory leaks detection
- Spotless for linting. A wrapper to ktlint
- Glide added for image loading & rendering
- Pre commit hook for lint check & auto fix(i.e unused imports).
- Github actions workflow added. run(unit tests, instrumentations tests, lint checks with spotless, build project)
- For Git work flow, I have used main branch as parent and then develop branch for accepting the PR.
- Instrumental Tests for Room DB and Retrofit Client
- Unit Tests for testing ViewModel with FakeRepository

# APIs used

- https://ws.audioscrobbler.com/2.0/?method=artist.search
- https://ws.audioscrobbler.com/2.0/?method=artist.gettopalbum
- https://ws.audioscrobbler.com/2.0/?method=album.getinfo


# Running Tests:

I have written a test task inside app_test_runner.gradle

- Run ./gradlew runUnitTests to run the unit tests.
- Run ./gradlew runInstrumentationTests to run the instrumentations tests.
- Run ./gradlew spotlessApply to fix linting issues automatically.
- Run ./gradlew spotlessCheck for verifying the code via spotless(ktLint).
- Run ./gradlew build to build the project with gradle.

Test reports can be found under /app/build/reports/tests.

# Spotless Linting Klint:

- Reports can be found at 'app/build/reports/lint-results.html'


# GitHub Action added for CI:

On each push to main branch & each pull request created to the main branch.

- setting up the jdk 11.
- Setting emulator with default, google API's.
- Check linting with spotless.
- Running unit tests.
- Running instrumentation unit tests.
- Build the Project with gradle.
 
   
