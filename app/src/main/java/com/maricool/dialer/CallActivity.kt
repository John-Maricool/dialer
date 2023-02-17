package com.maricool.dialer

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class CallActivity : AppCompatActivity() {

    private lateinit var dialedNumberTextView: TextView
    private lateinit var timer: TextView
    private lateinit var contactImageView: ImageView
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    var number = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)


        dialedNumberTextView = findViewById(R.id.dialed_number)
        timer = findViewById(R.id.timer)
        contactImageView = findViewById(R.id.contact_image)


        val phoneNumber = intent.getStringExtra("phoneNumber")
        val imageUriString = intent.getStringExtra("imageUri")
        val imageUri = if (imageUriString != null) Uri.parse(imageUriString) else null

        accept()
        dialedNumberTextView.text = phoneNumber
        if (imageUri != null) {
            Glide.with(this).load(imageUri).circleCrop().into(contactImageView)
        }
        else {
            contactImageView.setImageResource(R.drawable.ic_baseline_account_circle_24)
        }

        val endCallButton = findViewById<ImageButton>(R.id.end_call_button)
        endCallButton.setOnClickListener {
            finish()
        }
    }

    fun accept() {
        handler = Handler()
        runnable = Runnable {
            if (number < 10) {
                timer.text = "00:0$number"
            } else if (number > 10 && number < 60) {
                timer.text = "00:$number"
            } else if (number > 59 && number % 60 < 10) {
                val second: Int = number / 60
                timer.text = second.toString() + ":0" + number % 60
            } else {
                val second: Int = number / 60
                timer.text = second.toString() + ":" + number % 60
            }
            number++
            handler.postDelayed(runnable, 1000)
        }
        handler.post(runnable)
    }
}
