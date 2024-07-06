package com.example.capturerestrictionsample

import android.app.Activity.ScreenCaptureCallback
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.WindowManager.SCREEN_RECORDING_STATE_VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    val screenCaptureCallback = ScreenCaptureCallback {
        Log.d("CaptureRestrictionSample", "スクリーンショットを検出")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    }

    override fun onStart() {
        super.onStart()

        // OS 14以上の場合に使用できる機能
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            registerScreenCaptureCallback(mainExecutor, screenCaptureCallback)
        }
    }
    override fun onStop() {
        super.onStop()
        // OS 14以上の場合に使用できる機能
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            unregisterScreenCaptureCallback(screenCaptureCallback)
        }
    }

}