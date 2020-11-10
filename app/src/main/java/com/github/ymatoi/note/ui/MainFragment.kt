package com.github.ymatoi.note.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.stickyheader.StickyHeaderLinearLayoutManager
import com.github.ymatoi.note.R
import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), NotesController.Listener, SearchView.OnQueryTextListener {
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.editFragment)
        }

        val controller = NotesController(this)
        binding.notes.adapter = controller.adapter
        binding.notes.layoutManager = StickyHeaderLinearLayoutManager(requireContext())

        viewModel.notes.observe(viewLifecycleOwner) {
            controller.notes = it
        }

        binding.searchBar.setOnQueryTextListener(this)

        binding.account.setOnClickListener { _, isSignIn ->
            when (isSignIn) {
                true -> findNavController().navigate(R.id.accountFragment)
                else -> findNavController().navigate(R.id.signInFragment)
            }
        }
    }

    override fun onNoteClick(note: Note) = View.OnClickListener {
        val action = MainFragmentDirections.actionMainFragmentToEditFragment(note)
        findNavController().navigate(action)
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
