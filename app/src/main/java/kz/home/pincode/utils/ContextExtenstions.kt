package kz.home.pincode.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun Context.makeToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.vibrate(durationMillis: Long) {
    val vibrator = getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(
            VibrationEffect.createOneShot(
                durationMillis,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    } else {
        vibrator.vibrate(durationMillis)
    }
}

fun Context.share(text: String, chooserTitle: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
    startActivity(Intent.createChooser(shareIntent, chooserTitle))
}
fun Context.email(email: String, subject: String, text: String, chooserTitle: String) {
    val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
    emailIntent.putExtra(Intent.EXTRA_EMAIL, email)
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    emailIntent.putExtra(Intent.EXTRA_TEXT, text)
    startActivity(Intent.createChooser(emailIntent, chooserTitle))
}

fun Context.callNumber(phoneNumber: String) {
    val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    startActivity(callIntent)
}

fun Context.openCamera() {
    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivity(cameraIntent)
}