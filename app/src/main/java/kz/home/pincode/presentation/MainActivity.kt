package kz.home.pincode.presentation

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kz.home.pincode.R
import kz.home.pincode.domain.PinCode
import kz.home.pincode.utils.makeToast
import kz.home.pincode.utils.vibrate
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val KEY_PIN = "PIN"
private const val KEY_COLOR = "COLOR"

private const val CORRECT_PIN = "1567"

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var enterPinCode: EnterPinCodeTextView
    private lateinit var smth: PinCode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterPinCode = findViewById(R.id.enterPinCodeTextView)

        viewModel.pinLiveData.observe(this) {
            smth = it
            enterPinCode.setEnteredCode(it.value, 1)
        }

        initDigitButtons()
        onClearButtonClicked()
        onOkButtonClicked()
    }

    //override fun onSaveInstanceState(outState: Bundle) {
    //    super.onSaveInstanceState(outState)
    //    outState.putString(KEY_PIN, enterPinCode.getCode())
    //    outState.putInt(KEY_COLOR, enterPinCode.getColor())
    //}

    //override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    //    super.onRestoreInstanceState(savedInstanceState)
    //    val color = savedInstanceState.getInt(KEY_COLOR)
    //    enterPinCode.setEnteredCode(savedInstanceState.getString(KEY_PIN).toString(), color)
    //}

    private fun onOkButtonClicked() {
        findViewById<Button>(R.id.buttonOk).setOnClickListener {
            if (enterPinCode.getCode() == CORRECT_PIN) {
                makeToast("Correct PIN")
                val intent = Intent(this, NextActivity::class.java)
                startActivity(intent)
            } else {
                enterPinCode.wrongPinEntered()
                val animationShake = AnimationUtils.loadAnimation(this, R.anim.shake)
                enterPinCode.startAnimation(animationShake)
                vibrate(500)
                makeToast("Wrong PIN")
            }
        }
    }

    private fun onClearButtonClicked() {
        val buttonClear: Button = findViewById(R.id.buttonClear)
        buttonClear.setOnClickListener {
            enterPinCode.dropLast()
        }
        buttonClear.setOnLongClickListener {
            enterPinCode.erase()
            return@setOnLongClickListener true
        }
    }

    private fun initDigitButtons() {
        findViewById<Button>(R.id.button0).setOnClickListener {
            enterPinCode.setDigit("0")
        }
        findViewById<Button>(R.id.button1).setOnClickListener {
            enterPinCode.setDigit("1")
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            enterPinCode.setDigit("2")
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            enterPinCode.setDigit("3")
        }
        findViewById<Button>(R.id.button4).setOnClickListener {
            enterPinCode.setDigit("4")
        }
        findViewById<Button>(R.id.button5).setOnClickListener {
            enterPinCode.setDigit("5")
        }
        findViewById<Button>(R.id.button6).setOnClickListener {
            enterPinCode.setDigit("6")
        }
        findViewById<Button>(R.id.button7).setOnClickListener {
            enterPinCode.setDigit("7")
        }
        findViewById<Button>(R.id.button8).setOnClickListener {
            enterPinCode.setDigit("8")
        }
        findViewById<Button>(R.id.button9).setOnClickListener {
            enterPinCode.setDigit("9")
        }
    }
}