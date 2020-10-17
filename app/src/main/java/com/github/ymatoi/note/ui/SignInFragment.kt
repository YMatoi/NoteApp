package com.github.ymatoi.note.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.ymatoi.note.R
import com.github.ymatoi.note.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val viewModel: SignInViewModel by viewModels()
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        when (it?.resultCode) {
            Activity.RESULT_OK -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                runCatching {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                }.onFailure {
                    Timber.e(it)
                }
            }
            else -> {
                Timber.e("$it")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignInBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.signInButton.setOnClickListener {
            signIn()
        }

        auth = Firebase.auth
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_signin_web_client_id))
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        launcher.launch(googleSignInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Snackbar.make(requireView(), resources.getString(R.string.sign_in), Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Timber.e(it.exception)
            }
        }
    }
}
