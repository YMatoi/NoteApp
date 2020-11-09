package com.github.ymatoi.note.util

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.github.ymatoi.note.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountImage(context: Context, attr: AttributeSet) : AppCompatImageView(context, attr) {
    private val currentUser = Firebase.auth.currentUser

    init {
        if (currentUser == null) {
            load(R.drawable.ic_baseline_account_circle_24)
        } else {
            load(currentUser.photoUrl) {
                transformations(CircleCropTransformation())
            }
        }

        setOnClickListener {
            if (currentUser == null) {
                findNavController().navigate(R.id.signInFragment)
            } else {
                findNavController().navigate(R.id.accountFragment)
            }
        }
    }
}
