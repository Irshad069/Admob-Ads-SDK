package com.sdkads.adaptivebanner

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.sdkads.utils.AdsConfig

/**
 * Created by Irshad khan
 * Date: 12/11/2024$
 */

/**
 * A custom view that displays an adaptive banner ad using Google AdMob.
 * The ad adjusts its size dynamically to fit the screen width and orientation.
 *
 * @constructor Creates the `AdaptiveBannerView` programmatically or via XML.
 * @param context The context of the view.
 * @param attrs The attribute set for XML configuration.
 */
class AdaptiveBannerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    // The AdView instance for displaying the banner ad.
    private var adView: AdView? = null

    init {
        // Initializes the adaptive banner ad setup.
        setUpAdaptiveBanner()
    }

    /**
     * Sets up the adaptive banner view.
     * and the AdView is initialized.
     */
    private fun setUpAdaptiveBanner() {
            initializeAdView()
    }

    /**
     * Initializes the AdView instance for the banner ad.
     * The ad unit ID is configured, and the adaptive ad size is set.
     */
    private fun initializeAdView() {
        adView = AdView(context).apply {
            adUnitId = AdsConfig.ADAPTIVE_BANNER_ID // Ad unit ID from configuration.
            setAdSize(getAdaptiveBannerAdSize()) // Set adaptive banner ad size.
        }
        adView?.let { addView(it) } // Add the AdView to the layout.
        loadAd()
    }

    /**
     * Loads the ad into the AdView using an `AdRequest`.
     */
    private fun loadAd() {
        adView?.loadAd(AdRequest.Builder().build())
    }

    /**
     * Called when the view is attached to a window. Resumes the ad to ensure
     * it stays active when the view becomes visible.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        adView?.resume()
    }

    /**
     * Called when the view is detached from a window. Destroys the ad to
     * release resources and prevent memory leaks.
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        adView?.destroy()
    }

    /**
     * Calculates the adaptive banner ad size based on the screen width and density.
     * This ensures the banner fits the device's screen dimensions dynamically.
     *
     * @return The calculated `AdSize` for the adaptive banner.
     */
    private fun getAdaptiveBannerAdSize(): AdSize {
        val displayMetrics = resources.displayMetrics
        val adWidthPixels = displayMetrics.widthPixels
        val density = displayMetrics.density
        val adWidth = (adWidthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
    }
}