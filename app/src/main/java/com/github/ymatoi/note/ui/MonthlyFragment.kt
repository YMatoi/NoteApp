package com.github.ymatoi.note.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.ymatoi.note.R
import com.github.ymatoi.note.databinding.FragmentMonthlyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthlyFragment : Fragment(R.layout.fragment_monthly) {
    private var _binding: FragmentMonthlyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MonthlyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMonthlyBinding.bind(view)
        binding.viewModel = viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
