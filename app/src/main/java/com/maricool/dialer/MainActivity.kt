package com.maricool.dialer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var phoneNumberEditText: EditText
    private lateinit var contactImageView: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null

    private lateinit var telephonyManager: TelephonyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        phoneNumberEditText = findViewById(R.id.phone_number)

        contactImageView = findViewById(R.id.contact_image)
        contactImageView.setOnClickListener { openGallery() }

        // Set up button click listeners
        val button1: Button = findViewById(R.id.button_1)
        button1.setOnClickListener { appendDigitToNumber("1") }

        val button2: Button = findViewById(R.id.button_2)
        button2.setOnClickListener { appendDigitToNumber("2") }

        val button3: Button = findViewById(R.id.button_3)
        button3.setOnClickListener { appendDigitToNumber("3") }

        val button4: Button = findViewById(R.id.button_4)
        button4.setOnClickListener { appendDigitToNumber("4") }

        val button5: Button = findViewById(R.id.button_5)
        button5.setOnClickListener { appendDigitToNumber("5") }

        val button6: Button = findViewById(R.id.button_6)
        button6.setOnClickListener { appendDigitToNumber("6") }

        val button7: Button = findViewById(R.id.button_7)
        button7.setOnClickListener { appendDigitToNumber("7") }

        val button8: Button = findViewById(R.id.button_8)
        button8.setOnClickListener { appendDigitToNumber("8") }

        val button9: Button = findViewById(R.id.button_9)
        button9.setOnClickListener { appendDigitToNumber("9") }

        val buttonStar: Button = findViewById(R.id.button_star)
        buttonStar.setOnClickListener { appendDigitToNumber("*") }

        val button0: Button = findViewById(R.id.button_0)
        button0.setOnClickListener { appendDigitToNumber("0") }

        val buttonHash: Button = findViewById(R.id.button_hash)
        buttonHash.setOnClickListener { appendDigitToNumber("#") }

        val buttonBackspace: Button = findViewById(R.id.button_backspace)
        buttonBackspace.setOnClickListener { removeLastDigitFromNumber() }

        val buttonCall: Button = findViewById(R.id.button_call)
        buttonCall.setOnClickListener { callNumber() }
    }

    private fun appendDigitToNumber(digit: String) {
        phoneNumberEditText.append(digit)
    }

    private fun removeLastDigitFromNumber() {
        val currentNumber = phoneNumberEditText.text.toString()
        if (currentNumber.isNotEmpty()) {
            phoneNumberEditText.setText(currentNumber.substring(0, currentNumber.length - 1))
        }
    }

    private fun callNumber() {
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        simulateCall()
    }

    private fun simulateCall() {
        val phoneNumber = phoneNumberEditText.text.toString()

            // Simulate the phone ringing
            val ringingIntent = Intent(this, CallRingingActivity::class.java).apply {
                putExtra("phoneNumber", phoneNumber)
                putExtra("imageUri", intent.getStringExtra("imageUri"))
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(ringingIntent)
          }


    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            contactImageView.setImageURI(imageUri)
        }
    }
}
