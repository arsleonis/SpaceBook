package com.zrz.android.spacebook.util.helper

import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class EndSnapHelper : LinearSnapHelper() {
    private var verticalHelper: OrientationHelper? = null
    private var horizontalHelper: OrientationHelper? = null

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager, targetView: View
    ): IntArray? {
        val out = IntArray(2)
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToEnd(targetView, getHorizontalHelper(layoutManager))
        }
        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToEnd(targetView, getVerticalHelper(layoutManager))
        }
        return out
    }

    private fun distanceToEnd(targetView: View, helper: OrientationHelper): Int {
        val childEnd = helper.getDecoratedEnd(targetView)
        val containerEnd = helper.endAfterPadding
        return childEnd - containerEnd
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (verticalHelper == null || verticalHelper?.layoutManager !== layoutManager) {
            verticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return verticalHelper!!
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (horizontalHelper == null || horizontalHelper?.layoutManager !== layoutManager) {
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return horizontalHelper!!
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        if (layoutManager.canScrollVertically()) {
            return findEndView(layoutManager, getVerticalHelper(layoutManager))
        } else if (layoutManager.canScrollHorizontally()) {
            return findEndView(layoutManager, getHorizontalHelper(layoutManager))
        }
        return null
    }

    private fun findEndView(
        layoutManager: RecyclerView.LayoutManager, helper: OrientationHelper
    ): View? {
        val childCount = layoutManager.childCount
        if (childCount == 0) {
            return null
        }
        var closestChild: View? = null
        val layoutEnd = helper.endAfterPadding
        var absClosest = Integer.MAX_VALUE
        for (i in 0 until childCount) {
            val child = layoutManager.getChildAt(i)
            val childEnd = helper.getDecoratedEnd(child)
            val absDistance = abs(layoutEnd - childEnd)
            if (absDistance < absClosest) {
                absClosest = absDistance
                closestChild = child
            }
        }
        return closestChild
    }
}