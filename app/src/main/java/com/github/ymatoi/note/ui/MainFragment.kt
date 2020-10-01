package com.github.ymatoi.note.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ymatoi.note.R
import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main), NotesController.Listener {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: FragmentMainBinding
    private val controller = NotesController(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.editFragment)
        }

        binding.notes.adapter = controller.adapter
        binding.notes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        viewModel.notes.observe(viewLifecycleOwner, Observer {
            controller.notes = it
        })
    }

    override fun onNoteClick(note: Note) {
        val action = MainFragmentDirections.actionMainFragmentToEditFragment(note)
        findNavController().navigate(action)
    }
}
