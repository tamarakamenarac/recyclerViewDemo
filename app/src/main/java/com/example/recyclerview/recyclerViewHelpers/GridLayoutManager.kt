package com.example.recyclerview.recyclerViewHelpers

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class GridLayoutManager(context: Context?, orientation: Int, val numberOfRows: Int, val numberOfColumns: Int, reverseLayout: Boolean) :
    GridLayoutManager(
        context,
        numberOfRows,
        orientation,
        reverseLayout
    ) {

    private val enterInterpolator = AnticipateOvershootInterpolator(2f)

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return spanLayoutSize(super.generateDefaultLayoutParams())
    }

    override fun generateLayoutParams(
        context: Context?,
        attrs: AttributeSet?
    ): RecyclerView.LayoutParams {
        return spanLayoutSize(super.generateLayoutParams(context, attrs))
    }

    override fun generateLayoutParams(layoutParams: ViewGroup.LayoutParams?): RecyclerView.LayoutParams {
        return spanLayoutSize(super.generateLayoutParams(layoutParams))
    }

    private fun spanLayoutSize(layoutParams: RecyclerView.LayoutParams): RecyclerView.LayoutParams {
        layoutParams.width = getHorizontalSpace() / numberOfColumns
        return layoutParams
    }

    override fun checkLayoutParams(layoutParams: RecyclerView.LayoutParams?): Boolean {
        return super.checkLayoutParams(layoutParams)
    }

    private fun getHorizontalSpace(): Int {
        return width - paddingRight - paddingLeft
    }

    override fun addView(child: View, index: Int) {
        super.addView(child, index)
        child.alpha = 0.3f
        child.animate().translationY(0f).alpha(1f)
            .setInterpolator(enterInterpolator).duration = 500L
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        val linearSmoothScroller: LinearSmoothScroller =
            object : LinearSmoothScroller(recyclerView.context) {
                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                    return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
                }
            }
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    companion object {
        private const val MILLISECONDS_PER_INCH = 1000f
    }
}