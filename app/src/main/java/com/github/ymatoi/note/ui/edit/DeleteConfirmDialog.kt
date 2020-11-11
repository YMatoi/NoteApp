package com.github.ymatoi.note.ui.edit

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.github.ymatoi.note.R

class DeleteConfirmDialog(
    private var onPositiveButtonListener: DialogInterface.OnClickListener
) : DialogFragment() {
    companion object {
        const val TAG = "DeleteConfirmDialog"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
            .setMessage(R.string.confirm_delete_dialog_message)
            .setPositiveButton(R.string.delete, onPositiveButtonListener)
            .setNegativeButton(R.string.cancel) { _, _ ->
                dismiss()
            }
            .create()
    }
}
