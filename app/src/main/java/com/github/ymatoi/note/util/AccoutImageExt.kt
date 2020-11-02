package com.github.ymatoi.note.util

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import coil.load
import com.github.ymatoi.note.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

fun ImageView.setAccountImage(context: Context) {
    val currentUser = Firebase.auth.currentUser
    if (currentUser != null) {
        load(currentUser.photoUrl)
    } else {
        load(ContextCompat.getDrawable(context, R.drawable.ic_baseline_account_circle_24))
    }
}

fun ImageView.setAccountImageClickListener() {
    this.setOnClickListener {
        val currentUser = Firebase.auth.currentUser
        if (currentUser == null) {
            findNavController().navigate(R.id.signInFragment)
        } else {
            findNavController().navigate(R.id.accountFragment)
        }
    }
}
