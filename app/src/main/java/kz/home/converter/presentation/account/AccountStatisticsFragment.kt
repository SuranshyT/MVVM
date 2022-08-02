package kz.home.converter.presentation.account

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import kz.home.converter.R

class AccountStatisticsFragment(
    //val timeSpend: () -> Long
) :
    Fragment(R.layout.account_fragment_statistics) {
    private lateinit var numberOfConvertations: TextView
    private lateinit var timeInApp: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numberOfConvertations = view.findViewById(R.id.numberOfConvertations)

        timeInApp = view.findViewById(R.id.timeSpend)

        //val handler = Handler()
        //val run: Runnable = object : Runnable {
            //override fun run() {
                //updateTime()
                //handler.postDelayed(this, 1000)
            //}
        //}
        //handler.post(run)
    }

    fun updateConvertations(convertationCounter: Int) {
        numberOfConvertations.text = convertationCounter.toString()
    }

    //fun updateTime() {
        //val passedSeconds = TimeUnit.MILLISECONDS.toSeconds(timeSpend())
        //val seconds = passedSeconds % 60
        //val minutes = passedSeconds / 60 % 60
        //val hours = passedSeconds / 3600
        //timeInApp.text = String.format("%02d : %02d : %02d", hours, minutes, seconds)
    //}
}