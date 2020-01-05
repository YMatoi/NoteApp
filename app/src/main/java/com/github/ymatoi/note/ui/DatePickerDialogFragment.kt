package com.github.ymatoi.note.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.github.ymatoi.note.R

class DatePickerDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val numberPicker = NumberPicker(context)

        return AlertDialog.Builder(context)
            .setView(numberPicker)
            .setPositiveButton(R.string.ok) { dialog: DialogInterface?, which: Int ->
            }
            .create()
    }
}
