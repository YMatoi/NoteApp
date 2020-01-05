package com.github.ymatoi.note.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.github.ymatoi.note.R

class DatePickerDialogFragment : DialogFragment() {
    private val args: DatePickerDialogFragmentArgs by navArgs()

    var onPositiveButtonClickListener = { index: Int ->
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val numberPicker = NumberPicker(context).apply {
            minValue = 0
            maxValue = args.dates.lastIndex
            if (args.dates.isNotEmpty()) displayedValues = args.dates
        }

        return AlertDialog.Builder(context)
            .setView(numberPicker)
            .setPositiveButton(R.string.ok) { _, _ ->
                val index = numberPicker.value
                onPositiveButtonClickListener(index)
            }
            .create()
    }
}
