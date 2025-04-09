package com.sdkads.interstitial

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.sdkads.core.AdsConfig

/**
 * Created by Irshad khan
 * Date: 12/11/2024$
 */

/**
 * A helper object for managing interstitial ads using Google AdMob.
 * Handles ad loading, displaying, and lifecycle management.
 */
object InterstitialHelper {

    // The InterstitialAd instance for displaying full-screen ads.
    private var interstitialAd: InterstitialAd? = null

    // Flag to track whether an ad is currently being loaded.
    private var isAdLoading = false

    // Tag for logging purposes.
    private const val TAG = "InterstitialHelper"

    /**
     * Loads an interstitial ad using the configured ad unit ID.
     * This method ensures that only one ad is loaded at a time.
     *
     * @param context The context for loading the ad.
     */
    private fun loadAd(context: Context) {
        val adUnitId = AdsConfig.INTERSTITIAL_AD_ID
        if (adUnitId.isEmpty()) {
            Log.e(TAG, "Interstitial Ad Unit ID is not configured.")
            return
        }

        if (isAdLoading || interstitialAd != null) {
            return // Skip loading if an ad is already being loaded or available.
        }

        isAdLoading = true
        val adRequest = AdRequest.Builder().build()

        // Load the interstitial ad with a callback for success or failure.
        InterstitialAd.load(

            context,
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    Log.d(TAG, "Interstitial ad loaded.")
                    interstitialAd = ad
                    isAdLoading = false
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e(TAG, "Failed to load interstitial ad: ${adError.message}")
                    isAdLoading = false
                }
            }
        )
    }

    fun initLoadAd(context: Context) {
        loadAd(context)
    }

    /**
     * Displays the interstitial ad if available. If not, it attempts to load a new ad.
     *
     * @param activity The activity context for showing the ad.
     * @param onAdClosed A callback that is triggered when the ad is dismissed.
     */
    fun showAd(activity: Activity, onAdClosed: () -> Unit) {
        if (AdsConfig.INTERSTITIAL_AD_ID.isEmpty()) {
            Log.e(TAG, "Interstitial Ad Unit ID is not set.")
            onAdClosed() // Call the callback immediately if ad is not configured.
            return
        }

        if (interstitialAd != null) {

            // Set up the callback for the ad's full-screen content lifecycle.
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Interstitial ad dismissed.")
                    interstitialAd = null
                    onAdClosed()
                    loadAd(activity) // Load the next ad after the current one is dismissed.
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.e(TAG, "Failed to show interstitial ad: ${adError.message}")
                    interstitialAd = null
                    onAdClosed()
                    loadAd(activity) // Load a new ad if showing fails.
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Interstitial ad shown.")
                }
            }
            interstitialAd?.show(activity) // Show the interstitial ad.
        } else {
            Log.d(TAG, "Interstitial ad not ready. Loading...")
            onAdClosed() // Call the callback immediately if ad is not ready.
            loadAd(activity) // Attempt to load a new ad.
        }
    }
}