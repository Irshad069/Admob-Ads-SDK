package com.sdkads.collapsiblebannerview

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.sdkads.core.AdsConfig

/**
 * Created by Irshad Khan
 * Date: 09/04/2025
 *
 * A custom view that displays a collapsible banner ad using Google AdMob.
 * This view is intended to be used in your application to show an adaptive banner ad
 * that can collapse or expand, depending on the design requirements.
 * It also allows the ad to adapt to the screen width dynamically.
 */
class CollapsibleBannerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    /**
     * The AdView instance for showing the banner ad.
     */
    private var adView: AdView? = null

    /**
     * Initializes the custom view.
     * Sets up the banner ad and applies layout properties such as orientation and gravity.
     */
    init {
        orientation = VERTICAL // Set the orientation of the layout to vertical.
        gravity = Gravity.CENTER // Center the content (the ad) in the layout.
        setupCollapsibleBanner() // Set up the banner ad.
    }

    /**
     * Sets up the collapsible banner by initializing the AdView.
     */
    private fun setupCollapsibleBanner() {
        initializeAdView() // Initialize the AdView to load and display the ad.
    }

    /**
     * Initializes the AdView and configures its properties.
     * Sets the AdUnitId, AdSize, and creates an AdRequest with required extras.
     */
    private fun initializeAdView() {
        // Create and configure an AdView for displaying banner ads.
        adView = AdView(context).apply {
            adUnitId = AdsConfig.COLLAPSIBLE_BANNER_ID // Set the AdUnitId from AdsConfig.
            setAdSize(getAdaptiveBannerAdSize()) // Set the ad size dynamically based on the screen width.
        }

        // Create a Bundle of extras to specify additional parameters for the ad.
        val extras = Bundle().apply {
            putString("collapsible", "bottom") // Indicate the collapsible behavior of the ad.
        }

        // Build the AdRequest and add the extras.
        val adRequest = AdRequest.Builder()
            .addNetworkExtrasBundle(AdMobAdapter::class.java, extras) // Add AdMob specific extras.
            .build()

        // Load the ad into the AdView.
        adView?.loadAd(adRequest)

        // Add the AdView to the layout.
        adView?.let { addView(it) }
    }

    /**
     * Returns an adaptive banner ad size based on the current screen width.
     * This method dynamically adjusts the ad size to fit the screen.
     *
     * @return AdSize The size of the adaptive banner ad.
     */
    private fun getAdaptiveBannerAdSize(): AdSize {
        val displayMetrics = resources.displayMetrics // Get the screen's display metrics.
        val adWidthPixels = displayMetrics.widthPixels // Get the screen width in pixels.
        val density = displayMetrics.density // Get the screen's density.
        val adWidth = (adWidthPixels / density).toInt() // Calculate the width of the ad in dp (density-independent pixels).

        // Return the adaptive banner size for the current orientation and screen width.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
    }

    /**
     * Called when the view is attached to the window.
     * Resumes the AdView to start receiving ads and rendering them.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        adView?.resume() // Resume the AdView to ensure it can display ads.
    }

    /**
     * Called when the view is detached from the window.
     * Destroys the AdView to release resources when the view is no longer needed.
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        adView?.destroy() // Clean up the AdView and free up resources.
    }
}
