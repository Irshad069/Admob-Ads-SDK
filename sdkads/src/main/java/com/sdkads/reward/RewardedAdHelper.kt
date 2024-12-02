package com.sdkads.reward

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.sdkads.utils.AdsConfig

/**
 * A helper object for managing Rewarded Ads using Google AdMob.
 * Handles ad loading, displaying, and lifecycle management.
 */
object RewardedAdHelper {

    // The RewardedAd instance for displaying rewarded ads.
    private var rewardedAd: RewardedAd? = null

    // Flag to track whether an ad is currently being loaded.
    private var isAdLoading = false

    // Tag for logging purposes.
    private const val TAG = "RewardedAdHelper"

    /**
     * Loads a Rewarded Ad using the configured ad unit ID.
     * This method ensures that only one ad is loaded at a time.
     *
     * @param activity The activity context for loading the ad.
     */
    private fun loadAd(activity: Activity) {
        val adUnitId = AdsConfig.REWARDED_AD_ID
        if (adUnitId.isEmpty()) {
            Log.e(TAG, "Rewarded Ad Unit ID is not configured.")
            return
        }

        if (isAdLoading || rewardedAd != null) {
            return // Skip loading if an ad is already being loaded or available.
        }

        isAdLoading = true
        val adRequest = AdRequest.Builder().build()

        // Load the Rewarded Ad with a callback for success or failure.
        RewardedAd.load(
            activity,
            adUnitId,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAG, "Rewarded ad loaded.")
                    rewardedAd = ad
                    isAdLoading = false
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e(TAG, "Failed to load rewarded ad: ${adError.message}")
                    isAdLoading = false
                }
            }
        )
    }

    /**
     * Displays the Rewarded Ad if available. If not, it attempts to load a new ad.
     *
     * @param activity The activity context for showing the ad.
     * @param onAdClosed A callback triggered when the ad is dismissed.
     * @param onUserEarnedReward A callback triggered when the user earns a reward.
     */
    fun showAd(
        activity: Activity,
        onAdClosed: () -> Unit,
        onUserEarnedReward: (rewardAmount: Int, rewardType: String) -> Unit
    ) {
        if (AdsConfig.REWARDED_AD_ID.isEmpty()) {
            Log.e(TAG, "Rewarded Ad Unit ID is not set.")
            onAdClosed() // Call the callback immediately if ad is not configured.
            return
        }

        if (rewardedAd != null) {
            // Set up the callback for the ad's full-screen content lifecycle.
            rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Rewarded ad dismissed.")
                    rewardedAd = null
                    onAdClosed()
                    loadAd(activity) // Load the next ad after the current one is dismissed.
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.e(TAG, "Failed to show rewarded ad: ${adError.message}")
                    rewardedAd = null
                    onAdClosed()
                    loadAd(activity) // Load a new ad if showing fails.
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Rewarded ad shown.")
                }
            }

            // Show the ad and handle the reward callback.
            rewardedAd?.show(activity) { rewardItem ->
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
                Log.d(TAG, "User earned reward: $rewardAmount $rewardType")
                onUserEarnedReward(rewardAmount, rewardType)
            }
        } else {
            Log.d(TAG, "Rewarded ad not ready. Loading...")
            onAdClosed() // Call the callback immediately if ad is not ready.
            loadAd(activity) // Attempt to load a new ad.
        }
    }
}
