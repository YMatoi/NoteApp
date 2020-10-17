package com.github.ymatoi.note.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.ymatoi.note.R
import com.github.ymatoi.note.databinding.FragmentAccountBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment(R.layout.fragment_account) {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAccountBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.signOutButton.setOnClickListener {
            Firebase.auth.signOut()
            Snackbar.make(requireView(), resources.getString(R.string.sign_out), Snackbar.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
