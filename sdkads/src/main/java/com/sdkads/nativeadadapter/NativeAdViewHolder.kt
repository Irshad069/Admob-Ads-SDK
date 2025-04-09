package com.sdkads.nativeadadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sdkads.nativeview.CustomNativeAdView

/**
 * Created by Irshad Khan
 * Date: 09/04/2025$
 *
 * A ViewHolder that holds and manages a [CustomNativeAdView] for displaying
 * native ads inside a RecyclerView.
 *
 * @param itemView The view representing the native ad layout. Expected to be an instance of [CustomNativeAdView].
 */
class NativeAdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // Cast the itemView to CustomNativeAdView since this ViewHolder is specifically for ads
    private val customNativeAdView: CustomNativeAdView = itemView as CustomNativeAdView

    /**
     * Loads a native ad into the [CustomNativeAdView].
     * This method should be called when binding this ViewHolder.
     */
    fun loadAd() {
        customNativeAdView.loadNativeAd()
    }
}
