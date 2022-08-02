package kz.home.converter.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import kz.home.converter.GetImageWorker
import kz.home.converter.IMAGE_URI_KEY
import kz.home.converter.R
import kz.home.converter.SendAnalyticsWorker
import kz.home.converter.presentation.converter.ConverterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    //private val accountFragment = AccountFragment(
        //timeSpend = { timeSpend() }
        //)
    //private val converterFragment = ConverterFragment(
        //updateConvertations = { accountFragment.adapter.accountStatisticsFragment.updateConvertations(it) }
    //)
    private var elapsedTime = 0L
    private var startTime = 0L
    private lateinit var navHostFragment: NavHostFragment
    private val viewModel: ConverterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTime = System.currentTimeMillis()

        viewModel.getCurrencies()

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setupWithNavController(navController)

        initializeWorkManager()

        /*FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            Toast.makeText(this, task.result, Toast.LENGTH_SHORT).show()
            Log.d("MainActivity", task.result)
        }*/
    }

    fun timeSpend(): Long {
        elapsedTime = (System.currentTimeMillis() - startTime)
        return elapsedTime
    }

    private fun initializeWorkManager() {
        val tag = "TASKS"

        WorkManager.getInstance(this).getWorkInfosByTagLiveData(tag)
            .observe(this) { mutableList ->
                Log.d("", mutableList.toString())
                mutableList.forEach { workInfo ->
                    Log.d("", "${workInfo.id} ${workInfo.state} ${workInfo.progress}")
                }
            }

        val task1 = OneTimeWorkRequest.Builder(GetImageWorker::class.java)
            .setInputData(
                workDataOf(
                    IMAGE_URI_KEY to "https://www.worldometers.info//img/flags/small/tn_kz-flag.gif"
                )
            )
            .addTag(tag)
            .build()

        val task2 = OneTimeWorkRequest.Builder(SendAnalyticsWorker::class.java)
            .addTag(tag)
            .build()

        WorkManager.getInstance(this)
            .beginWith(task1)
            .then(task2)
            .enqueue()
    }
}