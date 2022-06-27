package kz.home.pincode.presentation

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import kz.home.pincode.R

class EnterPinCodeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var cellList: List<TextView>
    private val enterTextColor = ContextCompat.getColor(context, R.color.stroke)
    private val wrongTextColor = ContextCompat.getColor(context, R.color.wrong_text)
    private val enteredDrawable = R.drawable.text_shape_enter
    private val emptyDrawable = R.drawable.text_shape_empty
    private val wrongDrawable = R.drawable.text_shape_wrong
    private val enterCase = 1
    private val wrongCase = 2
    private val emptyCase = 3

    init {
        val view = inflate(context, R.layout.item_enter_pincode, this)

        cellList = listOf(
            view.findViewById(R.id.tv1),
            view.findViewById(R.id.tv2),
            view.findViewById(R.id.tv3),
            view.findViewById(R.id.tv4)
        )

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.EnterPinCodeTextView,
            0, 0
        ).apply {

            try {
                cellList[0].text = this.getString(R.styleable.EnterPinCodeTextView_cell1)
                cellList[1].text = this.getString(R.styleable.EnterPinCodeTextView_cell2)
                cellList[2].text = this.getString(R.styleable.EnterPinCodeTextView_cell3)
                cellList[3].text = this.getString(R.styleable.EnterPinCodeTextView_cell4)
                invalidate()
            } finally {
                recycle()
            }
        }
    }

    fun setDigit(number: String) {
        for (i in cellList.indices) {
            if (cellList[i].text.isBlank()) {
                enter(cellList[i], number)
                break
            }
        }
    }

    fun setEnteredCode(text: String, case: Int) {
        for (i in text.length downTo 1) {
            writePinInCells(text, i)
        }
        if (text.length == cellList.size) {
            if (case == enterCase) {
                for (j in cellList.indices) {
                    changeColor(cellList[j], enterCase)
                }
            } else {
                wrongPinEntered()
            }
        }
    }

    fun dropLast() {
        for (i in cellList.size-1 downTo 0) {
            if (cellList[i].text.isNotBlank()) {
                changeColor(cellList[i], emptyCase)
                if (i == cellList.size-1) {
                    for (j in 0 until cellList.size - 1) {
                        changeColor(cellList[j], enterCase)
                    }
                }
                break
            }
        }
    }

    fun erase() {
        for (i in cellList.indices) {
            changeColor(cellList[i], emptyCase)
        }
    }

    fun getCode(): String {
        var code = ""
        for (i in cellList.indices) {
            code += cellList[i].text.toString()
        }
        return code
    }

    fun wrongPinEntered() {
        for (i in cellList.indices) {
            changeColor(cellList[i], wrongCase)
        }
    }

    fun getColor(): Int {
        return if (cellList[0].currentTextColor == wrongTextColor) {
            wrongCase
        } else {
            enterCase
        }
    }

    private fun enter(cell: TextView, number: String) {
        cell.text = number
        cell.setBackgroundResource(enteredDrawable)
    }

    private fun writePinInCells(text: String, size: Int) {
        for (i in 0 until size) {
            cellList[i].text = text[i].toString()
            changeColor(cellList[i], enterCase)
        }
    }

    private fun changeColor(cell: TextView, case: Int) {
        when (case) {
            enterCase -> {
                cell.setTextColor(enterTextColor)
                cell.setBackgroundResource(enteredDrawable)
            }
            wrongCase -> {
                cell.setTextColor(wrongTextColor)
                cell.setBackgroundResource(wrongDrawable)
            }
            emptyCase -> {
                cell.text = ""
                cell.setTextColor(enterTextColor)
                cell.setBackgroundResource(emptyDrawable)
            }
        }
    }
}