package com.github.ymatoi.note.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DeleteConfirmDialog : DialogFragment() {
    companion object {
        const val TAG = "DeleteConfirmDialog"
    }

    var onPositiveButtonListener = { dialog: DialogInterface, which: Int ->
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
            .setMessage("削除しますか")
            .setPositiveButton("削除", onPositiveButtonListener)
            .setNegativeButton("キャンセル") { _, _ ->
                dismiss()
            }
            .create()
    }
}
