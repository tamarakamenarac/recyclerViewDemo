package com.example.recyclerview.recyclerViewHelpers

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class SnapHelper(private val manager: GridLayoutManager) : PagerSnapHelper() {
    private var horizontalHelper: OrientationHelper? = null

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        return getStartView(getHorizontalHelper(manager))
    }

    private fun getStartView(helper: OrientationHelper): View? {
        val firstVisibleChildPosition = manager.findFirstVisibleItemPosition()
        val lastCompletelyVisibleChildPosition = manager.findLastCompletelyVisibleItemPosition()
        val lastChildPosition = manager.itemCount - 1

        if (firstVisibleChildPosition != RecyclerView.NO_POSITION) {
            var childView = manager.findViewByPosition(firstVisibleChildPosition)
            if (manager.reverseLayout) {
                if (helper.getDecoratedEnd(childView) > helper.getDecoratedMeasurement(childView) / 2) {
                    childView = manager.findViewByPosition(firstVisibleChildPosition - 1)
                } else if (lastCompletelyVisibleChildPosition == lastChildPosition) {
                    childView = manager.findViewByPosition(lastChildPosition)
                }
                return childView
            }
            else {
                if (helper.getDecoratedEnd(childView) < helper.getDecoratedMeasurement(childView) / 2) {
                    childView = manager.findViewByPosition(firstVisibleChildPosition + 1)
                } else if (lastCompletelyVisibleChildPosition == lastChildPosition) {
                    childView = manager.findViewByPosition(lastChildPosition)
                }
                return childView
            }
        }
        return null
    }

    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray =
        intArrayOf(distanceToStart(targetView, getHorizontalHelper(layoutManager)), 0)

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {
        val currentView = findSnapView(manager) ?: return RecyclerView.NO_POSITION
        var currentPosition = manager.getPosition(currentView)
        val offset = manager.numberOfRows * manager.numberOfColumns

        val reversedLayout = manager.reverseLayout

        currentPosition = if (currentPosition <= offset) {
            0
        } else {
            offset
        }

        return if (reversedLayout) {
            if (velocityX < 0) {
                swipeToLaterItems(currentPosition, offset, layoutManager)
            } else {
                (currentPosition - offset).coerceAtLeast(0)
            }
        } else {
            if (velocityX < 0) {
                (currentPosition - offset).coerceAtLeast(0)
            } else {
                swipeToLaterItems(currentPosition, offset, layoutManager)
            }
        }
    }

    private fun swipeToLaterItems(
        currentPosition: Int,
        offset: Int,
        layoutManager: RecyclerView.LayoutManager
    ): Int {
        return if (currentPosition + offset + 1 >= layoutManager.itemCount) {
            currentPosition
        } else {
            currentPosition + offset
        }
    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper): Int =
        helper.getDecoratedStart(targetView) - helper.startAfterPadding

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (horizontalHelper == null) {
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return horizontalHelper!!
    }
}