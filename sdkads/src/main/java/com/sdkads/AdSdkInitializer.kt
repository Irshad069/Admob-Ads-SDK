package com.sdkads

import android.app.Activity
import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.sdkads.appopen.AppOpenAdHelper
import com.sdkads.consent.ConsentManager
import com.sdkads.core.AdsConfig
import com.sdkads.interstitial.InterstitialHelper

/**
 * Created by Irshad Khan
 * Date: 12/11/2024
 *
 * A singleton object responsible for initializing and managing the SDK Ads system.
 * It handles:
 * - Google Mobile Ads SDK initialization
 * - Ad unit configuration (banner, interstitial, native, etc.)
 * - App Open ad setup
 * - Interstitial ad preloading
 * - Consent form handling (via ConsentManager)
 */
object AdSdkInitializer{

    // Manages App Open ads and excluded activity logic
    private var appOpenAdHelper: AppOpenAdHelper? = null

    // Prevents multiple loading attempts of interstitials
    private var isInterstitialLoaded = false


    /**
     * Initializes the Google Mobile Ads SDK, sets ad unit IDs, configures test devices, and
     * prepares interstitial and App Open ads.
     *
     * @param application The application instance required by MobileAds.
     * @param isDebug Flag to indicate if the app is in debug mode.
     * @param testDeviceIds A list of test device IDs used to show test ads.
     * @param bannerId AdMob banner ad unit ID.
     * @param appOpenAd AdMob App Open ad unit ID.
     * @param interstitialAd AdMob interstitial ad unit ID.
     * @param adaptiveBannerAd AdMob adaptive banner ad unit ID.
     * @param rewardInterstitialAd AdMob rewarded interstitial ad unit ID.
     * @param rewardAd AdMob rewarded ad unit ID.
     * @param nativeAd AdMob native ad unit ID.
     * @param collapsibleBannerAd AdMob collapsible banner ad unit ID.
     * @param excludedActivities List of activity class names to exclude from showing App Open ads.
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
        nativeAd: String = "",
        collapsibleBannerAd: String = "",
        excludedActivities: List<String> = emptyList()
    ) {
        // Set test device IDs for AdMob
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(testDeviceIds)
                .build()
        )
        // Set all ad unit IDs to AdsConfig (shared config holder)
        AdsConfig.BANNER_ID = bannerId
        AdsConfig.APP_OPEN_ID = appOpenAd
        AdsConfig.IS_DEBUG = isDebug
        AdsConfig.INTERSTITIAL_AD_ID = interstitialAd
        AdsConfig.ADAPTIVE_BANNER_ID = adaptiveBannerAd
        AdsConfig.REWARD_INTERSTITIAL_AD_ID = rewardInterstitialAd
        AdsConfig.REWARDED_AD_ID = rewardAd
        AdsConfig.NATIVE_AD_ID = nativeAd
        AdsConfig.COLLAPSIBLE_BANNER_ID = collapsibleBannerAd

        // Initialize Google Mobile Ads SDK
        MobileAds.initialize(application) {
            // Once initialized, load interstitial ads if not already loaded

            if (!isInterstitialLoaded) {
                isInterstitialLoaded = true
                // Load the ad after MobileAds initialized
                Log.e("TAG", "initialize: before load" )
                InterstitialHelper.initLoadAd(application)
                Log.e("TAG", "initialize: after load")
            }
        }

        // Setup App Open ad handler with excluded activities
        appOpenAdHelper = AppOpenAdHelper(application, excludedActivities)

    }

    /**
     * Requests user consent using Google’s Consent SDK and invokes a callback with the result.
     * Must be called before showing ads in GDPR regions.
     *
     * @param activity The current activity context required to show consent form.
     * @param onConsentResult Callback to return whether user has granted consent:
     * - `true` = Consent granted, show ads.
     * - `false` = Consent not granted, restrict ads.
     */
    fun handleConsent(activity: Activity, onConsentResult: (Boolean) -> Unit) {
        ConsentManager.requestConsent(activity) { consentGiven ->
            onConsentResult(consentGiven)
        }
    }
}
