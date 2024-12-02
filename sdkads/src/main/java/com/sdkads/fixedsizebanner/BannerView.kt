package com.sdkads.fixedsizebanner

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.sdkads.utils.AdsConfig

/**
 * A custom view for displaying a fixed-size banner ad using Google AdMob.
 * This view always displays a banner of predefined size (`AdSize.BANNER`).
 *
 * @constructor Creates the `BannerView` programmatically or via XML.
 * @param context The context of the view.
 * @param attrs The attribute set for XML configuration.
 */
class BannerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    // The AdView instance for displaying the banner ad.
    private var adView: AdView? = null

    init {
        // Initializes the banner ad setup.
        setupBanner()
    }

    /**
     * Sets up the banner ad view. If ads are enabled, the view is made visible
     * and the AdView is initialized. Otherwise, it is hidden.
     */
    private fun setupBanner() {
        if (AdsConfig.areAdsEnabled) {
            visibility = View.VISIBLE
            initializeAdView()
        } else {
            visibility = View.GONE
        }
    }

    /**
     * Initializes the AdView instance for the banner ad.
     * The ad unit ID and fixed size (`AdSize.BANNER`) are configured.
     */
    private fun initializeAdView() {
        adView = AdView(context).apply {
            adUnitId = AdsConfig.BANNER_ID // Ad unit ID from configuration.
            setAdSize(AdSize.BANNER) // Use the fixed size banner.
        }
        adView?.let { addView(it) } // Add the AdView to the layout.
    }

    /**
     * Called when the view is attached to a window.
     * Ensures the ad is loaded and resumed when the view becomes visible.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (AdsConfig.areAdsEnabled) {
            if (adView == null) {
                initializeAdView() // Reinitialize AdView if it was removed.
            }
            adView?.loadAd(AdRequest.Builder().build())
        }
    }

    /**
     * Called when the view is detached from a window.
     * Destroys the ad to release resources and prevent memory leaks.
     */

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        adView?.destroy()
    }
}

