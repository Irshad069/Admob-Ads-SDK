package com.sdkadmobads.utils

import android.view.View
import android.widget.TextView
import com.sdkadmobads.data.MyItemModel
import com.sdkads.nativeadadapter.BindableViewHolder

class ViewHolder(itemView: View) : BindableViewHolder<MyItemModel>(itemView) {
    private val titleTextView: TextView = itemView.findViewById(com.sdkadmobads.R.id.item_title)
    private val idTextView: TextView = itemView.findViewById(com.sdkadmobads.R.id.item_id)

    override fun bind(item: MyItemModel) {
        titleTextView.text = item.title
        idTextView.text = item.id.toString()
    }
}
