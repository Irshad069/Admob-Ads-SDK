package com.sdkads.nativeadadapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View

/**
 * Created by Irshad Khan
 * Date: 09/04/2025$
 *
 * An abstract generic ViewHolder class that forces subclasses to implement a `bind` method.
 * This class is useful for RecyclerViews where each ViewHolder needs to bind different types of data.
 *
 * @param T The type of data that this ViewHolder will bind.
 * @param itemView The root view of the ViewHolder.
 */
abstract class BindableViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Abstract method that must be implemented by subclasses to bind the data [item] to the view.
     *
     * @param item The data item of type [T] to bind to the ViewHolder.
     */
    abstract fun bind(item: T)
}
