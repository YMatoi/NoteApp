package com.github.ymatoi.note.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ymatoi.note.R
import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.databinding.FragmentMainBinding
import com.github.ymatoi.note.util.setAccountImage
import com.github.ymatoi.note.util.setAccountImageClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), NotesController.Listener, SearchView.OnQueryTextListener {
    private val viewModel: MainViewModel by viewModels()
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

        viewModel.notes.observe(viewLifecycleOwner) {
            controller.notes = it
        }

        binding.searchBar.setOnQueryTextListener(this)

        binding.account.setAccountImageClickListener()
    }

    override fun onResume() {
        super.onResume()
        binding.account.setAccountImage(requireContext())
    }

    override fun onNoteClick(note: Note) = View.OnClickListener {
        val action = MainFragmentDirections.actionMainFragmentToEditFragment(note)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.setQuery(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.setQuery(newText)
        return false
    }
}
