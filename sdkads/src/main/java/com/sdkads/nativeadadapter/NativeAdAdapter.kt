package com.sdkads.nativeadadapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sdkads.nativeview.CustomNativeAdView

/**
 * Created by Irshad Khan
 * Date: 09/04/2025$
 *
 * A RecyclerView adapter that injects native ads at fixed intervals (based on [adFrequency])
 * into a list of items. It supports a combination of content items and AdMob native ads.
 *
 * @param context The context used to create views (especially for ads).
 * @param items The list of content items to be displayed in the RecyclerView.
 * @param adFrequency The frequency at which native ads are shown (e.g., every 5 items).
 * @param createViewHolder A lambda that creates the content ViewHolder from a ViewGroup.
 */
class NativeAdAdapter(
    private val context: Context,
    private val items: List<Any>,
    private val adFrequency: Int,
    private val createViewHolder: (ViewGroup) -> RecyclerView.ViewHolder
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_TYPE_CONTENT = 0
        private const val ITEM_TYPE_NATIVE_AD = 1
    }

    /**
     * Determines the view type at a given position.
     * If the position is divisible by (adFrequency + 1), it's a native ad.
     */
    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % (adFrequency + 1) == 0) {
            ITEM_TYPE_NATIVE_AD
        } else {
            ITEM_TYPE_CONTENT
        }
    }

    /**
     * Creates the appropriate ViewHolder depending on the view type.
     * For ads, a [CustomNativeAdView] is used; for content, the provided factory lambda is used.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_NATIVE_AD) {
            val adView = CustomNativeAdView(context)
            NativeAdViewHolder(adView)
        } else {
            createViewHolder(parent)
        }
    }

    /**
     * Binds the item or loads an ad at the specified position.
     * Calculates the real position of content by subtracting the number of inserted ads before it.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val actualPosition = position - (position / (adFrequency + 1))

        if (holder is NativeAdViewHolder) {
            holder.loadAd()
        } else {
            val item = items[actualPosition]
            if (holder is BindableViewHolder<*>) {
                // Type-safe unchecked cast is used here
                (holder as BindableViewHolder<Any>).bind(item)
            }
        }
    }

    /**
     * Calculates the total item count by including the number of ads to be inserted.
     */
    override fun getItemCount(): Int {
        return items.size + (items.size / adFrequency)
    }

}
