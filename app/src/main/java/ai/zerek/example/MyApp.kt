package ai.zerek.example

import android.app.Application
import ai.zerek.sdk.ZerekAiSdk


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        ZerekAiSdk.init(
            apiKey = "your api key")
    }
}