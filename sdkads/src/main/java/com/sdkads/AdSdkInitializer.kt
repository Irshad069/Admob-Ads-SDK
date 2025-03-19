package com.sdkads

import android.app.Activity
import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.sdkads.appopen.AppOpenAdHelper
import com.sdkads.consent.ConsentManager
import com.sdkads.core.AdsConfig

/**
 * Created by Irshad khan
 * Date: 12/11/2024$
 */

/**
 * Utility object for initializing and managing the advertisement SDK and user consent.
 * Handles the initialization of Google Mobile Ads and manages user consent for showing ads.
 */
object AdSdkInitializer {

    private var appOpenAdHelper: AppOpenAdHelper? = null


    /**
     * Initializes the Google Mobile Ads SDK with the required configuration.
     *
     * @param application The application instance used for initializing the Mobile Ads SDK.
     *
     * **Steps performed:**
     * 1. Configures test device IDs for testing ads.
     * 2. Initializes the Mobile Ads SDK.
     */
    fun initialize(
        application: Application,
        isDebug: Boolean,
        testDeviceIds: List<String> = emptyList(),
        bannerId: String = "",
        appOpenAd: String = "",
        interstitialAd: String = "",
        adaptiveBannerAd: String = "",
        rewardInterstitialAd: String = "",
        rewardAd: String = "",
        nativeAd: String = ""
    ) {

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(testDeviceIds)
                .build()
        )
        AdsConfig.BANNER_ID = bannerId
        AdsConfig.APP_OPEN_ID = appOpenAd
        AdsConfig.IS_DEBUG = isDebug
        AdsConfig.INTERSTITIAL_AD_ID = interstitialAd
        AdsConfig.ADAPTIVE_BANNER_ID = adaptiveBannerAd
        AdsConfig.REWARD_INTERSTITIAL_AD_ID = rewardInterstitialAd
        AdsConfig.REWARDED_AD_ID = rewardAd
        AdsConfig.NATIVE_AD_ID = nativeAd

        MobileAds.initialize(application) {
            // Callback invoked when initialization is complete.
        }

        // Set up App Open Ads
        appOpenAdHelper = AppOpenAdHelper(application)
    }

    /**
     * Handles user consent for showing ads using the Consent Management API.
     *
     * @param activity The activity context used to display the consent form.
     * @param onConsentResult A callback invoked with the user's consent decision:
     *  - `true`: Consent is given, ads are enabled.
     *  - `false`: Consent is not given, ads are disabled.
     *
     * **Steps performed:**
     * 1. Requests user consent using the `ConsentManager`.
     * 3. Invokes the provided callback with the consent result.
     */
    fun handleConsent(activity: Activity, onConsentResult: (Boolean) -> Unit) {
        ConsentManager.requestConsent(activity) { consentGiven ->
            onConsentResult(consentGiven)
        }
    }

    /**
     * Register the current activity to handle App Open Ads.
     *
     * @param activity The current activity to register.
     */
    fun registerActivity(activity: Activity) {
        appOpenAdHelper?.registerActivity(activity)
    }
}
