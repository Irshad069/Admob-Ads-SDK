package com.sdkads.appopen

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.sdkads.utils.AdsConfig
import java.util.Date

/**
 * AppOpenAdHelper is a utility class for managing App Open Ads in an Android application.
 *
 * This class handles:
 * - Loading App Open Ads.
 * - Displaying the ads when the app moves to the foreground.
 * - Managing ad lifecycle events such as loading, showing, dismissing, and expiration.
 *
 * It uses Google's Mobile Ads SDK to handle App Open Ads and integrates lifecycle observation
 * to automatically load and show ads at appropriate times.
 *
 * @param application The application instance to register lifecycle observers and manage ads.
 */
class AppOpenAdHelper(private val application: Application) : DefaultLifecycleObserver {

    /**
     * The currently loaded App Open Ad.
     */
    private var appOpenAd: AppOpenAd? = null

    /**
     * Tracks whether an ad is currently being loaded.
     */
    private var isLoadingAd = false

    /**
     * Tracks whether an ad is currently being displayed.
     */
    private var isShowingAd = false

    /**
     * The timestamp when the ad was loaded, used to check for expiration.
     */
    private var loadTime: Long = 0

    /**
     * Holds a reference to the currently active activity for showing ads.
     */
    private var currentActivity: Activity? = null

    init {
        // Register as a lifecycle observer to detect when the app enters the foreground.
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    /**
     * Loads an App Open Ad if no ad is currently being loaded or available.
     *
     * @param context The context to use for loading the ad.
     */
    fun loadAd(context: Context) {
        if (isLoadingAd || isAdAvailable()) return

        isLoadingAd = true
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            AD_UNIT_ID,
            request,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    isLoadingAd = false
                    loadTime = Date().time
                    Log.d(LOG_TAG, "Ad Loaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isLoadingAd = false
                    Log.d(LOG_TAG, "Ad Failed to Load: ${loadAdError.message}")
                }
            }
        )
    }

    /**
     * Displays the ad if available and not currently showing.
     *
     * @param activity The activity in which the ad will be shown.
     * @param onAdComplete A callback that will be executed after the ad is dismissed or fails to show.
     */
    fun showAdIfAvailable(activity: Activity, onAdComplete: () -> Unit = {}) {
        if (isShowingAd || !isAdAvailable()) {
            Log.d(LOG_TAG, "Ad not ready or already showing")
            onAdComplete()
            return
        }

        appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                appOpenAd = null
                isShowingAd = false
                Log.d(LOG_TAG, "Ad Dismissed")
                onAdComplete()
                loadAd(activity)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                appOpenAd = null
                isShowingAd = false
                Log.d(LOG_TAG, "Ad Failed to Show: ${adError.message}")
                onAdComplete()
                loadAd(activity)
            }

            override fun onAdShowedFullScreenContent() {
                isShowingAd = true
                Log.d(LOG_TAG, "Ad Showing")
            }
        }

        appOpenAd?.show(activity)
    }

    /**
     * Checks whether an ad is available and not expired.
     *
     * @return True if an ad is available and valid, false otherwise.
     */
    private fun isAdAvailable(): Boolean {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)
    }

    /**
     * Checks if the ad's load time is within a specified number of hours.
     *
     * @param hours The number of hours to validate.
     * @return True if the ad's load time is less than the specified hours ago, false otherwise.
     */
    private fun wasLoadTimeLessThanNHoursAgo(hours: Int): Boolean {
        val currentTime = Date().time
        return (currentTime - loadTime) < hours * 60 * 60 * 1000
    }

    /**
     * Lifecycle callback invoked when the app enters the foreground.
     *
     * @param owner The LifecycleOwner triggering the callback.
     */
    override fun onStart(owner: LifecycleOwner) {
        currentActivity?.let { showAdIfAvailable(it) }
    }

    /**
     * Registers the current activity for showing and loading ads.
     *
     * @param activity The activity to register.
     */
    fun registerActivity(activity: Activity) {
        currentActivity = activity
        if (!isAdAvailable()) {
            loadAd(activity)
        }
    }

    companion object {
        /**
         * Tag for logging messages.
         */
        private const val LOG_TAG = "AppOpenAdHelper"

        /**
         * The ad unit ID for loading App Open Ads, retrieved from build configuration.
         */
        private const val AD_UNIT_ID = AdsConfig.APP_OPEN_ID
    }
}

