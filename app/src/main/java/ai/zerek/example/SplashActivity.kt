package ai.zerek.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ai.zerek.example.R
import ai.zerek.sdk.ZerekAiSdk
import ai.zerek.sdk.data.listeners.APSplashScreenListener
import ai.zerek.sdk.data.models.APLocation


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Default Use Case
        ZerekAiSdk
            .newInstance(this)
            .showSplashScreen()

        // User-Centric Use Case
        ZerekAiSdk
            .newInstance(this)
            .setUserId("app_user_id")
            .setUserProperties(
                mapOf("age" to "25", "gender" to "male")
            )
            .setLocation(
                APLocation(
                    latitude = 37.7749,
                    longitude = 122.4194
                )
            )
            .showSplashScreen()

        // Drafts Enabled Mode Use Case
        ZerekAiSdk
            .newInstance(this)
            .showSplashScreen(hasDrafts = true)

        // Splash Screen Listener Use Case
        ZerekAiSdk
            .newInstance(this)
            .setSplashScreenListener(
                object: APSplashScreenListener {
                    override fun onFinish() {
                        // TODO: actions to do on the splash screen finish
                    }

                    override fun onRunAPCustomAction(params: HashMap<String, Any>) {
                        // TODO: your implementation of ZerekAi Custom Action
                    }
                }
            )
            .showSplashScreen()

        // All-in Use Case
        ZerekAiSdk
            .newInstance(this)
            .setUserId("app_user_id")
            .setUserProperties(
                mapOf("age" to "25", "gender" to "male")
            )
            .setLocation(
                APLocation(
                    latitude = 37.7749,
                    longitude = 122.4194
                )
            )
            .setSplashScreenListener(
                object: APSplashScreenListener {
                    override fun onFinish() {
                        // TODO: actions to do on the splash screen finish
                    }

                    override fun onRunAPCustomAction(params: HashMap<String, Any>) {
                        // TODO: your implementation of ZerekAi Custom Actions
                    }
                }
            )
            .showSplashScreen(hasDrafts = true)
    }

}