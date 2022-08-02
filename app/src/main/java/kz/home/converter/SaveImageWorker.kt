package kz.home.converter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

const val IMAGE_URI_KEY = "IMAGE_URI"

class GetImageWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val appContext = applicationContext
        val imageUriInput = inputData.getString(IMAGE_URI_KEY) ?: return Result.failure()
        try {
            val imageUrl = URL(imageUriInput)
            val picture = BitmapFactory.decodeStream(
                imageUrl.openConnection().getInputStream()
            )
            val output = Bitmap.createBitmap(
                picture.width, picture.height, picture.config
            )
            val outputDir = appContext.getDir("worker_output", Context.MODE_PRIVATE)
            if (!outputDir.exists()) {
                outputDir.mkdirs()
            }
            val outputFile = File(outputDir, "kz_flag.png")
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(outputFile)
                output.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
            } finally {
                out?.let {
                    try {
                        it.close()
                        //Log.e("", outputDir.list()[0])
                        //Log.e("", outputDir.list()[1])
                    } catch (ignore: IOException) {
                    }
                }
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Result.failure()
        }
        return Result.success()
    }
}