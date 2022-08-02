package kz.home.converter.presentation.converter

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import kz.home.converter.R

class DeleteItemDialogFragment(
    private val removeSelectedItem: () -> Unit,
    private val changeToolbar: () -> Unit,
    private val undoSnackbar: () -> Unit
) : DialogFragment(R.layout.delete_dialog_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        with(view) {
            findViewById<TextView>(R.id.dialogText).text = "Вы уверены, что хотите удалить валюту?"
            findViewById<Button>(R.id.cancelButton).setOnClickListener {
                dismiss()
            }
            findViewById<Button>(R.id.deleteButton).setOnClickListener {
                removeSelectedItem()
                dismiss()
                undoSnackbar()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        changeToolbar()
    }
}