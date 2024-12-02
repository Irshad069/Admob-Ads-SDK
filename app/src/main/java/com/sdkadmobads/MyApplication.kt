package com.sdkadmobads

import android.app.Application
import com.sdkads.AdSdkInitializer

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AdSdkInitializer.initialize(
            application = this
        )

    }
}
