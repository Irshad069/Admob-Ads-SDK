package com.sdkadmobads

import android.app.Application
import com.sdkadmobads.fragment.InterstitialFragment
import com.sdkads.AdSdkInitializer

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AdSdkInitializer.initialize(
            application = this,
            isDebug = BuildConfig.DEBUG,
            testDeviceIds = listOf("007DEB0576C579A6A35DA74A5E3C5186"),
            bannerId = BuildConfig.BANNER_ID,
            appOpenAd = BuildConfig.APP_OPEN_ID,
            interstitialAd = BuildConfig.INTERSTITIAL_AD_ID,
            adaptiveBannerAd = BuildConfig.ADAPTIVE_BANNER_ID,
            rewardInterstitialAd = BuildConfig.REWARD_INTERSTITIAL_AD_ID,
            rewardAd = BuildConfig.REWARDED_AD_ID,
            nativeAd = BuildConfig.NATIVE_AD_ID,
            collapsibleBannerAd = BuildConfig.COLLAPSIBLE_BANNER_ID,
            excludedActivities = listOf(
//                MainActivity::class.java.canonicalName
            ),
        )

    }
}
