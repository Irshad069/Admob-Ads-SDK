package com.sdkads.appopen

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.sdkads.core.AdsConfig
import java.util.Date

/**
 * Created by Irshad khan
 * Date: 12/11/2024$
 */

/**
 * AppOpenAdHelper is a helper class designed to handle loading, showing, and managing the lifecycle of App Open Ads.
 * It uses the Google Mobile Ads SDK to load and display full-screen app open ads when the app comes to the foreground.
 *
 * The class observes the app lifecycle and shows the app open ad when the app is resumed, as long as the ad is available and not already being shown.
 *
 * @param application The application instance used to register lifecycle observers.
 * @param excludedActivities List of activity names that should not trigger the display of an app open ad.
 */
class AppOpenAdHelper(
    private val application: Application,
    private val excludedActivities: List<String> = emptyList()
) : DefaultLifecycleObserver, Application.ActivityLifecycleCallbacks {

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
        application.registerActivityLifecycleCallbacks(this)
    }

    /**
     * Called when the app enters the foreground (app is visible to the user).
     * This method attempts to show the app open ad if it's available and the current activity is not excluded.
     *
     * @param owner The LifecycleOwner triggering the callback.
     */
    override fun onStart(owner: LifecycleOwner) {
        currentActivity?.let {
            if (!excludedActivities.contains(it::class.java.name)) {
                showAdIfAvailable(it)
            }
        }
    }

    /**
     * Loads an App Open Ad from the Google Ads SDK.
     * If an ad is already being loaded or is already available, it does nothing.
     *
     * @param context The context (activity) used to load the ad.
     */
    fun loadAd(context: Activity) {
        if (isLoadingAd || isAdAvailable()) return

        isLoadingAd = true
        val request = AdRequest.Builder().build()

        // Load the App Open Ad with the specified ad unit ID.
        AppOpenAd.load(
            context,
            AdsConfig.APP_OPEN_ID,
            request,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    isLoadingAd = false
                    loadTime = Date().time
                    Log.d(TAG, "App Open Ad loaded.")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isLoadingAd = false
                    Log.e(TAG, "Failed to load App Open Ad: ${loadAdError.message}")
                }
            }
        )
    }

    /**
     * Checks if an App Open Ad is available to be shown.
     * An ad is considered available if it was loaded within the last 4 hours.
     *
     * @return True if the ad is available, false otherwise.
     */
    private fun isAdAvailable(): Boolean {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)
    }

    /**
     * Checks if the ad was loaded within the last N hours.
     *
     * @param numHours The number of hours to check against.
     * @return True if the ad was loaded within the specified time frame.
     */
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Int): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    /**
     * Tries to show the App Open Ad if available.
     * If an ad is not available or is currently being shown, it loads a new ad.
     *
     * @param activity The current activity in which the ad will be shown.
     */
    fun showAdIfAvailable(activity: Activity) {
        Log.d(TAG, "Trying to show App Open Ad in ${activity::class.java.simpleName}")
        Log.e(TAG, "AdActivity name: ${AdActivity::class.java.canonicalName}" )
        Log.e(TAG, "current activity name: ${currentActivity?.javaClass?.canonicalName}" )


        // Avoid showing ads if the current activity is the ad activity (which is a special activity for displaying ads).
        if (AdActivity::class.java.canonicalName == currentActivity?.javaClass?.canonicalName) {
            Log.e(TAG, "Since this is ad activity then we cannot show app open it")
            return
        }

        // If ad is already being shown or unavailable, load a new ad and return.
        if (isShowingAd || !isAdAvailable() || isExcludedActivity()) {
            loadAd(activity)
            return
        }

        // Show the loaded ad.
        isShowingAd = true
        appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                appOpenAd = null
                isShowingAd = false
                Log.d(TAG, "Ad dismissed.")
                loadAd(activity)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                appOpenAd = null
                isShowingAd = false
                Log.e(TAG, "Ad failed to show: ${adError.message}")
                loadAd(activity)
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed.")
            }
        }
        appOpenAd?.show(activity)
    }

    /**
     * Checks if the current activity is in the excluded activities list.
     * This helps to prevent showing ads in certain activities, such as splash screens or login screens.
     *
     * @return True if the current activity is excluded from showing ads.
     */
    private fun isExcludedActivity(): Boolean {
        return excludedActivities.any { it == currentActivity?.javaClass?.canonicalName }
    }

    // Activity lifecycle methods to track the current activity.
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        if (!isAdAvailable() && !isLoadingAd) {
            currentActivity = activity
        }
        Log.e(TAG, "on activity start current activity named: ${currentActivity?.javaClass?.canonicalName} ")
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
        Log.e(TAG, "on resumed current activity named: ${currentActivity?.javaClass?.canonicalName} ")
    }

    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}

    companion object {
        private const val TAG = "AppOpenAdHelper"
    }
}
