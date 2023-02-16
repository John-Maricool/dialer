package com.maricool.dialer

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class CallRingingActivity : AppCompatActivity() {

    private lateinit var phoneNumber: String
    private lateinit var imageUri: String
    var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_ringing)

        // Get the dialed phone number and contact image URI from the intent extras
        phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
        imageUri = intent.getStringExtra("imageUri") ?: ""

        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        with(mediaPlayer) {
            this?.setLooping(true);
            this?.start()
        };
        // Display the caller's information
        val callerInfoTextView = findViewById<TextView>(R.id.caller_info_text_view)
        callerInfoTextView.text = "Incoming call from $phoneNumber"
        if (imageUri.isNotEmpty()) {
            val contactImage = findViewById<ImageView>(R.id.contact_image)
            contactImage.setImageURI(Uri.parse(imageUri))
        }

        // Set up buttons to answer or reject the call
        val answerButton = findViewById<Button>(R.id.answer_button)
        answerButton.setOnClickListener {
            val answerIntent = Intent(this, CallActivity::class.java).apply {
                putExtra("phoneNumber", phoneNumber)
                putExtra("imageUri", imageUri)
            }
            with(mediaPlayer) {
                this?.stop()
            }
            startActivity(answerIntent)
            finish()
        }

        val rejectButton = findViewById<Button>(R.id.reject_button)
        rejectButton.setOnClickListener {
            with(mediaPlayer) {
                this?.stop()
            }
            finish()
        }
    }
}
