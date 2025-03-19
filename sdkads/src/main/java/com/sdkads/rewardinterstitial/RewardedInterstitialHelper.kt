package com.sdkads.rewardinterstitial

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.sdkads.core.AdsConfig

/**
 * Created by Irshad khan
 * Date: 12/11/2024$
 */

/**
 * A helper object for managing Rewarded Interstitial Ads using Google AdMob.
 * Handles ad loading, displaying, and lifecycle management.
 */
object RewardedInterstitialHelper {

    // The RewardInterstitialAd instance for displaying full-screen ads.
    private var rewardedInterstitialAd: RewardedInterstitialAd? = null

    // Flag to track whether an ad is currently being loaded.
    private var isAdLoading = false

    // Tag for logging purposes.
    private const val TAG = "RewardedInterstitialHelper"

    /**
     * Loads a Rewarded Interstitial Ad using the configured ad unit ID.
     *
     * @param activity The activity context for loading the ad.
     */
    private fun loadAd(activity: Activity) {
        val adUnitId = AdsConfig.REWARD_INTERSTITIAL_AD_ID
        if (adUnitId.isEmpty()) {
            Log.e(TAG, "Rewarded Interstitial Ad Unit ID is not configured.")
            return
        }

        if (isAdLoading || rewardedInterstitialAd != null) {
            return // Skip loading if an ad is already being loaded or available.
        }

        isAdLoading = true
        val adRequest = AdRequest.Builder().build()

        // Load the Rewarded Interstitial Ad with a callback for success or failure.
        RewardedInterstitialAd.load(
            activity,
            adUnitId,
            adRequest,
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    Log.d(TAG, "Rewarded Interstitial Ad loaded.")
                    rewardedInterstitialAd = ad
                    isAdLoading = false
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e(TAG, "Failed to load Rewarded Interstitial Ad: ${adError.message}")
                    isAdLoading = false
                }
            }
        )
    }

    /**
     * Displays the Rewarded Interstitial Ad if available. If not, it attempts to load a new ad.
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
        if (AdsConfig.REWARD_INTERSTITIAL_AD_ID.isEmpty()) {
            Log.e(TAG, "Rewarded Interstitial Ad Unit ID is not set.")
            onAdClosed() // Call the callback immediately if ad is not configured.
            return
        }

        if (rewardedInterstitialAd != null) {
            // Set up the callback for the ad's full-screen content lifecycle.
            rewardedInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Rewarded Interstitial Ad dismissed.")
                        rewardedInterstitialAd = null
                        onAdClosed()
                        loadAd(activity) // Load the next ad after the current one is dismissed.
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.e(TAG, "Failed to show Rewarded Interstitial Ad: ${adError.message}")
                        rewardedInterstitialAd = null
                        onAdClosed()
                        loadAd(activity) // Load a new ad if showing fails.
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Rewarded Interstitial Ad shown.")
                    }
                }

            // Show the ad and handle the reward callback.
            rewardedInterstitialAd?.show(activity) { rewardItem ->
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
                Log.d(TAG, "User earned reward: $rewardAmount $rewardType")
                onUserEarnedReward(rewardAmount, rewardType)
            }
        } else {
            Log.d(TAG, "Rewarded Interstitial Ad not ready. Loading...")
            onAdClosed() // Call the callback immediately if ad is not ready.
            loadAd(activity) // Attempt to load a new ad.
        }
    }
}
