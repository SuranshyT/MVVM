package kz.home.converter.presentation.account

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import kz.home.converter.R

class AccountMainFragment : Fragment(R.layout.account_fragment_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonShare: Button = view.findViewById(R.id.button_share)
        val buttonEmail: Button = view.findViewById(R.id.button_email)
        val buttonCall: Button = view.findViewById(R.id.button_call)
        val buttonCamera: Button = view.findViewById(R.id.button_camera)
        val sharedText = getString(
            R.string.shared_text, getString(R.string.name), getString(R.string.email), getString(
                R.string.phone
            ))

        buttonShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, sharedText)
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_chooser_title)))
        }

        buttonEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            emailIntent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.email))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
            emailIntent.putExtra(Intent.EXTRA_TEXT, sharedText)
            startActivity(Intent.createChooser(emailIntent, getString(R.string.email_chooser_title)))
        }

        buttonCall.setOnClickListener {
            val callIntent =
                Intent(Intent.ACTION_DIAL, Uri.parse("tel:${getString(R.string.phone)}"))
            startActivity(callIntent)
        }

        buttonCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(cameraIntent)
        }
    }
}