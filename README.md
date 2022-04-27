# [ZerekAI Android SDK](https://zerek.ai/)

## Requirements
- minSdkVersion 16

*Examples provided in Kotlin programming language*

## Installation
Add the following dependency to your app's `build.gradle` file:

```groovy
dependencies {
    implementation 'ai.zerek:sdk:1.0.0'
}
```

### Maven central
Add the following to your root build.gradle file
```groovy
allprojects {
    repositories {
      mavenCentral()
    }
}
```

*Do not forget to sync project with gradle files afterwards*

## Add ZerekAI API base URL

Put API base url into your app's manifest file inside `<application>` tag

```xml
<manifest ...>
    <application ...>
        <meta-data
            android:name="zerekAiBaseApiUrl"
            android:value="ZerekAI API base url hosted by your company"
            />
    </application>
</manifest>
```

!!! BE CAREFUL !!! It is possible that your company hosts several development environments, thus the API base url depends on the environment

## Initialization
Register an account in the admin panel of [ZerekAI](https://zerek.ai/)

Initialize ZerekAiSdk on app startup and pass the **API key** that you received upon account registration

```kotlin
// App StartUp
class YourApp: Application() {
    override fun onCreate() {
        super.onCreate()

        ZerekAiSdk
            .init(apiKey = "your api key")
    }
}
```
### Initialization Exception
SDK throws `APInitializationException` on `newInstance` method call if **ZerekAiApiKey** is not provided beforehand via `init` method

## ZerekAiView (Banner & Story)
You can visit the admin panel and create some content. Do not forget to change the status of the content to **active**. Then create **ZerekAiView** instance or add it in xml and specify APViewId(e. g. `APV-00000000`).
```kotlin
class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zerekView = ZerekAiView(ctx).apply
        zerekView.setZerekAiViewId("APV-00000000")                
    }
}
```  

XML
```xml
<ai.zerek.sdk.ui.ZerekAiView
    android:id="@+id/zerekAiView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:apViewId="APV-00000000"
    />
```

## ZerekAiViewless (Instruction & PopUp) 

You can visit the admin panel and create some content. Do not forget to change the status of the content to **active**. When you call `preload` and `show` methods you need add created APViewId(e. g. `APV-00000000`) as argument. `preload` method preloads the Instruction(or PopUp) contents to your device, and `show` displaying it.

```kotlin
class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ZerekAiViewless(this).apply {
            preload("APV-00000000")
            show("APV-00000000")
        }
    }
}
```

Show ZerekAI Viewless at any suitable moment but note that 
`preload` must call before calling `show` method.

Also you can add **OnStoriesFinishedCallback**
```kotlin
class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zerekInstruction = ZerekAiInstruction(this)
        zerekInstruction.preload("APV-00000000")
        zerekInstruction.setOnDismissedListener {
            // TODO: dosomething when instructions finished 
        }
    }
}
```

If you are not able to observe the created content - probable reasons are:
- You forgot to activate the content in the ZerekAI admin panel
- Check again the integration guide, maybe you missed something out
- The SDK couldn't preload the contents on the previous `            preload` method calls due to network issues or internal sdk issues

### Custom Action Listener
```kotlin
interface APCustomActionListener {
    fun onRun(action: APCustomAction)
}
```
```kotlin
data class APCustomAction(
    val name: String?,
    val parameters: HashMap<String, Any>?
) : Serializable
```
For listening of the events - you should provide your implementation of `APCustomActionListener`:
```kotlin
    ZerekAiView(this).apply(
        setZerekAiViewId("APV-00000000")
        setAPCustomActionListener(
            object: APCustomActionListener {
                override fun onRun(action: APCustomAction) {
                    // TODO: your implementation of ZerekAi Custom Actions
                }
            }
        )
    )
```
OR
```kotlin
    ZerekAiInstruction(this).apply(
        setAPCustomActionListener(
            object: APCustomActionListener {
                override fun onRun(action: APCustomAction) {
                    // TODO: your implementation of ZerekAi Custom Actions
                }
            }
        )
    )
```
### Personalized Experience
In order to make SDK experience more personalized, you can provide following user data:
```kotlin
ZerekAiSdk
    .newInstance(context)
    .setUserId(userId)
    .setUserProperties(userProperties)
    .setLocation(userLocation)
```
`userId: String` - id assigned to the user by your own system/service, useful for identifying the same user across multiple devices\
`userProperties: Map<String, String>` - user properties, e.g. - age, gender, etc. User properties help SDK to select and show content relevant to the user. Ex:
```kotlin
val userProperties = mapOf("age" to "25", "gender" to "male")
```
`userLocation: APLocation` - user location (latitude & longitude). Required if you want to display geo-oriented content to the user
```kotlin
data class APLocation(
    val latitude: Double,
    val longitude: Double
) : Serializable
```

## ZerekAI Debug Mode
To observe network logs of the SDK - pass `true` to `setIsDebuggable` method:
```kotlin
ZerekAiSdk
    .setIsDebuggable(true)
```
OR
```kotlin
ZerekAiSdk
    .newInstance(context)
    .setIsDebuggable(true)
```
Do not forget to switch *Debug Mode* off for the release build of your app.

## Permissions
We include the [INTERNET](http://developer.android.com/reference/android/Manifest.permission.html#INTERNET) permission by default as we need it to make network requests:
```xml
<uses-permission android:name="android.permission.INTERNET" />
```
will be added to the manifest file during the app build process

## Dependency graph

Here is our complete dependency graph:
```
# Transitive (shared with your app)
org.jetbrains.kotlin:kotlin-stdlib:1.4.31

androidx.core:core-ktx:1.3.2
androidx.appcompat:appcompat:1.2.0
androidx.lifecycle:lifecycle-extensions:2.2.0
androidx.recyclerview:recyclerview:1.2.0
androidx.constraintlayout:constraintlayout:2.1.0-beta01
androidx.cardview:cardview:1.0.0

com.google.android.material:material:1.3.0
com.google.code.gson:gson:2.8.6

com.squareup.okhttp3:okhttp:4.9.1
com.squareup.okhttp3:logging-interceptor:4.9.1

com.github.bumptech.glide:glide:4.12.0
com.github.bumptech.glide:okhttp3-integration:4.12.0
com.github.bumptech.glide:compiler:4.12.0
```

### Transitive Dependencies
ZerekAI Android SDK transitively depends on the above libraries. If your app is using any one of these libraries, they should at least be on the same major version that ZerekAI SDK is using.
When there are two versions of a library at build time, Gradle automatically picks the newer version. 
This means if you are currently using say Glide 3.x, your app would automatically get Glide 4.x after including ZerekAi.
