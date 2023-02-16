package com.maricool.dialer

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class CallActivity : AppCompatActivity() {

    private lateinit var dialedNumberTextView: TextView
    private lateinit var contactImageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        dialedNumberTextView = findViewById(R.id.dialed_number)
        contactImageView = findViewById(R.id.contact_image)


        val phoneNumber = intent.getStringExtra("phoneNumber")
        val imageUriString = intent.getStringExtra("imageUri")
        val imageUri = if (imageUriString != null) Uri.parse(imageUriString) else null

        dialedNumberTextView.text = phoneNumber
        if (imageUri != null) {
            contactImageView.setImageURI(imageUri)
        }
        else {
            contactImageView.setImageResource(R.drawable.ic_baseline_account_circle_24)
        }

        val endCallButton = findViewById<Button>(R.id.end_call_button)
        endCallButton.setOnClickListener {
            finish()
        }
    }
}
