package com.zrz.android.spacebook.util.extension

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

inline fun <T> RecyclerView.Adapter<*>.addItemDiff(
    oldList: List<T>, newList: List<T>,
    crossinline compare: (Pair<T, T>) -> Boolean
) {
    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            Log.d("ml", "---")
            Log.d("ml", "BEFORE $oldItemPosition $newItemPosition ${oldList[oldItemPosition]}")
            if (newItemPosition > oldList.size - 1) {
                return false
            } else {
                Log.d("ml", "---------------------------------")

                Log.d("ml", "oldList.size=${oldList.size}")
                Log.d("ml", "newList.size=${newList.size}")


                Log.d("ml", "BEFORE ${newList[newItemPosition]}")

                return compare(oldList[oldItemPosition] to newList[newItemPosition])

            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            if (newItemPosition > oldList.size - 1) {
                false
            } else {
                oldList[oldItemPosition] == newList[newItemPosition]
            }

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size
    })


    diff.dispatchUpdatesTo(this)
}