package com.maricool.dialer

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import java.lang.Math.abs

open class OnSwipeTouchListener(context: Context) : View.OnTouchListener {

    private val gestureDetector: GestureDetector

    // Tracks whether the user is currently swiping or not
    private var isSwiping = false

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Start the swipe animation
                startSwipeAnimation(view, event)
                isSwiping = true
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                // Update the swipe animation
                updateSwipeAnimation(view, event)
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (isSwiping) {
                    // End the swipe animation and call performClick()
                    endSwipeAnimation(view, event)
                    isSwiping = false
                    view.performClick()
                }
                return true
            }
            else -> return false
        }
    }

    private fun startSwipeAnimation(view: View, event: MotionEvent) {
        // Calculate the starting X position of the button
        val startX = view.x

        // Calculate the starting Y position of the button
        val startY = view.y

        // Save the starting position of the button in the view's tag
        view.tag = Pair(startX, startY)
    }

    private fun updateSwipeAnimation(view: View, event: MotionEvent) {
        // Calculate the new X position of the button based on the user's finger movement
        val newX = event.rawX - view.width / 2

        // Get the screen width
        val screenWidth = view.context.resources.displayMetrics.widthPixels

        // Calculate the maximum X position based on the screen width
        val maxPosition = screenWidth - view.width

        // Limit the X position to the maximum position
        view.x = newX.coerceIn(0f, maxPosition.toFloat())
    }

    private fun endSwipeAnimation(view: View, event: MotionEvent) {
        // Retrieve the starting position of the button from the view's tag
        val (startX, startY) = view.tag as Pair<Float, Float>

        // Calculate the distance that the button was swiped
        val swipeDistance = view.x - startX

        // Calculate the duration of the swipe animation based on the swipe distance
        val swipeDuration = abs(swipeDistance) / view.context.resources.displayMetrics.density

        // Create an animation to return the button to its original position
        val propertyX = PropertyValuesHolder.ofFloat(View.X, view.x, startX)
        val propertyY = PropertyValuesHolder.ofFloat(View.Y, view.y, startY)
        val animator = ObjectAnimator.ofPropertyValuesHolder(view, propertyX, propertyY)
        animator.duration = swipeDuration.toLong()

        // Start the animation
        animator.start()
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            // Handle single tap event
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            // Handle double tap event
            return true
        }
    }

    open fun onSwipeRight() {}
    open fun onSwipeLeft() {}
}
