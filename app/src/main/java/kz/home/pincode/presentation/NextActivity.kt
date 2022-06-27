package kz.home.pincode.presentation

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kz.home.pincode.R
import kz.home.pincode.utils.callNumber
import kz.home.pincode.utils.email
import kz.home.pincode.utils.openCamera
import kz.home.pincode.utils.share

class NextActivity : AppCompatActivity(R.layout.activity_next) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedText = getString(
            R.string.shared_text, getString(R.string.name), getString(R.string.email), getString(
                R.string.phone
            ))
        val buttonShare: Button = findViewById(R.id.button_share)
        val buttonEmail: Button = findViewById(R.id.button_email)
        val buttonCall: Button = findViewById(R.id.button_call)
        val buttonCamera: Button = findViewById(R.id.button_camera)

        buttonShare.setOnClickListener{
            share(sharedText,getString(R.string.share_chooser_title))
        }

        buttonEmail.setOnClickListener{
            email(getString(R.string.email), getString(R.string.email_subject), sharedText, getString(
                R.string.email_chooser_title
            ))
        }

        buttonCall.setOnClickListener{
            callNumber(getString(R.string.phone))
        }

        buttonCamera.setOnClickListener{
            openCamera()
        }
    }
}