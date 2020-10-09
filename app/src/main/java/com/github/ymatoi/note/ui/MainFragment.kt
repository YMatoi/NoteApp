package com.github.ymatoi.note.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
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
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val controller = NotesController(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
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

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setQuery(newText)
                return false
            }
        })
    }

    override fun onNoteClick(note: Note) = View.OnClickListener {
        val action = MainFragmentDirections.actionMainFragmentToEditFragment(note)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
