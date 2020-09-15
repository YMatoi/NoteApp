package com.github.ymatoi.note.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.ymatoi.note.databinding.FragmentEditBinding
import java.util.Calendar
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.concurrent.fixedRateTimer

class EditFragment : Fragment() {

    companion object {
        fun newInstance() = EditFragment()
    }

    private val viewModel: EditViewModel by viewModel()
    private lateinit var binding: FragmentEditBinding
    private val args: EditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.editMode(args.note)

        binding.deleteButton.setOnClickListener {
            DeleteConfirmDialog{ _, _ ->
                viewModel.deleteNote(it)
            }.show(parentFragmentManager, DeleteConfirmDialog.TAG)
        }

        binding.dateInputText.setOnClickListener {
            val calendar = viewModel.getRecordedAt()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val date = calendar.get(Calendar.DATE)

            DatePickerDialog(requireContext(), { _: DatePicker?, y: Int, m: Int, d: Int ->
                viewModel.setDate(y, m, d)
            }, year, month, date).show()
        }

        binding.timeInputText.setOnClickListener {
            val calendar = viewModel.getRecordedAt()
            val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(requireContext(), { _: TimePicker?, h: Int, m: Int ->
                viewModel.setTime(h, m)
            }, hourOfDay, minute, true).show()
        }
    }
}
