package com.github.ymatoi.note.util

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import coil.load
import com.github.ymatoi.note.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

class AccountImage(context: Context, attr: AttributeSet) : CircleImageView(context, attr) {
    private val currentUser = Firebase.auth.currentUser

    init {
        if (currentUser == null) {
            load(ContextCompat.getDrawable(context, R.drawable.ic_baseline_account_circle_24))
        } else {
            load(currentUser.photoUrl)
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
