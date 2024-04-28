
## Requirement
 - JDK: 11+
 - Android SDK
 - Optional: Android studio

## Architecture
- Architecture: MVVM with Clean Architecture
- Language: Kotlin
- Framework: Kotlin coroutines, AndroidX, okhttp
- Code quality: detekt
- UnitTest: Junit, Mockk
- Build: gradle, jenkins

## Build & Run
1. star server; The backend project can get by follow link, [Backend server](https://github.com/zzyy/BookManagementServer)
   - The default server is deployed on AWS; 
   - if you need use custom server, need change the `baseUrl` in `NetworkConst`
2. run `./gradlew :app:assembleProdRelease`, the apk will generated in build folder, then install in you phone 

## CI
Jenkins file has been added in project; 

you can modify the publish stage, to upload apk to you own repo

## Related projects
- backend project: [server](https://github.com/zzyy/BookManagementServer)
