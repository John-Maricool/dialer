package com.maricool.dialer

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.bumptech.glide.Glide

class CallRingingActivity : AppCompatActivity() {

    private lateinit var phoneNumber: String
    private lateinit var imageUri: String
    var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_ringing)

        // Get the dialed phone number and contact image URI from the intent extras
        phoneNumber = intent.getStringExtra("phoneNumber") ?: "Unknown"
        imageUri = intent.getStringExtra("imageUri") ?: ""

        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        with(mediaPlayer) {
            this?.isLooping = true;
            this?.start()
        };
        // Display the caller's information
        val callerInfoTextView = findViewById<TextView>(R.id.caller_info_text_view)
        callerInfoTextView.text = "Incoming call from $phoneNumber"
        if (imageUri.isNotEmpty()) {
            val contactImage = findViewById<ImageView>(R.id.contact_image)
            Glide.with(this).load(Uri.parse(imageUri)).circleCrop().into(contactImage)
        }

        // Set up buttons to answer or reject the call
        val answerButton = findViewById<ImageButton>(R.id.answer_button)
        answerButton.setOnClickListener {
            val answerIntent =
                Intent(this@CallRingingActivity, CallActivity::class.java).apply {
                    putExtra("phoneNumber", phoneNumber)
                    putExtra("imageUri", imageUri)
                }
            with(mediaPlayer) {
                this?.stop()
            }
            startActivity(answerIntent)
            finish()
        }
        answerButton.setOnTouchListener(object: OnSwipeTouchListener(this) {

            override fun onSwipeRight() {
                super.onSwipeRight()
                val originalX = answerButton.x
                val buttonWidth = answerButton.width.toFloat()

                val propertyX = PropertyValuesHolder.ofFloat(View.X, originalX, originalX + buttonWidth)

                val animator = ObjectAnimator.ofPropertyValuesHolder(answerButton, propertyX)
                animator.duration = 300

                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        answerButton.x = originalX
                        answerButton.performClick()
                    }
                })
                animator.start()
            }
        })

        val rejectButton = findViewById<ImageButton>(R.id.reject_button)
        rejectButton.setOnClickListener {
            with(mediaPlayer) {
                this?.stop()
            }
            finish()
        }
        rejectButton.setOnTouchListener(object: OnSwipeTouchListener(this) {

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                val originalX = rejectButton.x
                val buttonWidth = rejectButton.width.toFloat()

                val propertyX = PropertyValuesHolder.ofFloat(View.X, originalX, originalX + buttonWidth)

                val animator = ObjectAnimator.ofPropertyValuesHolder(rejectButton, propertyX)
                animator.duration = 300

                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        rejectButton.performClick()
                        answerButton.x = originalX
                    }
                })
                animator.start()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        with(mediaPlayer) {
            this?.stop()
        }
    }
}
