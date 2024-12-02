package com.sdkads.nativeview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.sdkads.R
import com.sdkads.utils.AdsConfig

/**
 * A custom view for displaying Google AdMob Native Ads in an Android application.
 * This class provides functionality to load and display native ads dynamically
 * using AdMob's Native Ad features.
 *
 * @constructor Creates a CustomNativeAdView instance with optional attributes.
 * @param context The context of the view.
 * @param attrs Optional XML attributes for configuring the view.
 */
class CustomNativeAdView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    // AdLoader to manage the loading of native ads.
    private var adLoader: AdLoader? = null

    // Reference to the currently loaded NativeAd object.
    private var nativeAd: NativeAd? = null

    // Determines the view type (e.g., "large" or "medium") based on attributes.
    private var viewType: String? = null

    init {
        attrs?.let {
            // Extracts custom attributes (e.g., viewType) from XML.
            val typedArray =
                context.theme.obtainStyledAttributes(it, R.styleable.NativeAdView, 0, 0)
            viewType = typedArray.getString(R.styleable.NativeAdView_viewType)
            typedArray.recycle()
        }
        // Initializes the setup for loading and displaying native ads.
        setupNativeAd()
    }

    /**
     * Sets up the native ad by checking if ads are enabled and initializing the view.
     * If ads are disabled, the view is hidden.
     */
    private fun setupNativeAd() {
        if (AdsConfig.areAdsEnabled) {
            visibility = View.VISIBLE
            removeAllViews()
            loadNativeAd()
        } else {
            visibility = View.GONE
        }
    }

    /**
     * Loads a native ad using AdMob's AdLoader and binds it to the appropriate layout.
     */
    private fun loadNativeAd() {
        // Determines the appropriate layout based on the view type.
        val adLayoutRes =
            if (viewType == AdsConfig.LARGE) R.layout.native_ad_view else R.layout.native_medium_ad_view

        val builder = AdLoader.Builder(context, AdsConfig.NATIVE_AD_ID)

        builder.forNativeAd { ad ->
            // Destroys the previous ad (if any) and updates the reference.
            nativeAd?.destroy()
            nativeAd = ad

            // Inflates the native ad layout and binds the ad to it.
            val nativeAdView =
                LayoutInflater.from(context).inflate(adLayoutRes, this, false) as NativeAdView
            bindAdToView(ad, nativeAdView)

            // Replaces any existing views with the new native ad view.
            removeAllViews()
            addView(nativeAdView)
        }.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(error: LoadAdError) {
                super.onAdFailedToLoad(error)
                // Optionally handle ad load failures (e.g., log errors or retry loading).
            }
        })

        // Configures video options for native ads.
        val videoOptions = VideoOptions.Builder().setStartMuted(true).build()
        val adOptions = NativeAdOptions.Builder().setVideoOptions(videoOptions).build()
        builder.withNativeAdOptions(adOptions)

        // Builds the AdLoader and starts loading an ad.
        adLoader = builder.build()
        adLoader?.loadAd(AdRequest.Builder().build())
    }

    /**
     * Binds a NativeAd object to the provided NativeAdView and sets up the views dynamically.
     *
     * @param ad The NativeAd object containing the ad content.
     * @param adView The NativeAdView instance where the ad will be displayed.
     */
    private fun bindAdToView(ad: NativeAd, adView: NativeAdView) {
        // Media content
        adView.findViewById<com.google.android.gms.ads.nativead.MediaView>(R.id.ad_media)?.apply {
            ad.mediaContent?.let {
                setMediaContent(it)
                visibility = View.VISIBLE
            } ?: run {
                visibility = View.GONE
            }
            adView.mediaView = this
        }

        // Headline
        adView.findViewById<TextView>(R.id.ad_headline)?.apply {
            text = ad.headline
            adView.headlineView = this
        }

        // Body text
        adView.findViewById<TextView>(R.id.ad_body)?.apply {
            text = ad.body
            visibility = if (ad.body == null) View.GONE else View.VISIBLE
            adView.bodyView = this
        }

        // Call to Action
        adView.findViewById<Button>(R.id.ad_call_to_action)?.apply {
            text = ad.callToAction
            visibility = if (ad.callToAction == null) View.GONE else View.VISIBLE
            adView.callToActionView = this
        }

        // App Icon
        adView.findViewById<ImageView>(R.id.ad_app_icon)?.apply {
            ad.icon?.let {
                setImageDrawable(it.drawable)
                visibility = View.VISIBLE
            } ?: run {
                visibility = View.GONE
            }
            adView.iconView = this
        }

        // Price
        adView.findViewById<TextView>(R.id.ad_price)?.apply {
            text = ad.price
            visibility = if (ad.price == null) View.GONE else View.VISIBLE
            adView.priceView = this
        }

        // Star Rating
        adView.findViewById<RatingBar>(R.id.ad_stars)?.apply {
            ad.starRating?.let { rating ->
                this.rating = rating.toFloat()
                visibility = View.VISIBLE
            } ?: run {
                visibility = View.GONE
            }
            adView.starRatingView = this
        }

        // Store
        adView.findViewById<TextView>(R.id.ad_store)?.apply {
            text = ad.store
            visibility = if (ad.store == null) View.GONE else View.VISIBLE
            adView.storeView = this
        }

        // Advertiser
        adView.findViewById<TextView>(R.id.ad_advertiser)?.apply {
            text = ad.advertiser
            visibility = if (ad.advertiser == null) View.GONE else View.VISIBLE
            adView.advertiserView = this
        }

        // Associates the native ad object with the NativeAdView.
        adView.setNativeAd(ad)
    }

    /**
     * Cleans up resources by destroying the native ad when the view is detached from the window.
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        nativeAd?.destroy()
    }
}
