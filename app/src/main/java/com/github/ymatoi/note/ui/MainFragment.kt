package com.github.ymatoi.note.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.ymatoi.note.R
import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.databinding.FragmentMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), NotesController.Listener, SearchView.OnQueryTextListener {
    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val controller = NotesController(this)
    private lateinit var firebaseAuth: FirebaseAuth

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

        firebaseAuth = Firebase.auth
        binding.account.setOnClickListener(accountOnClickListener)
    }

    private val accountOnClickListener = View.OnClickListener {
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            findNavController().navigate(R.id.signInFragment)
        } else {
            findNavController().navigate(R.id.accountFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        setAccountImage(firebaseAuth.currentUser)
    }

    override fun onNoteClick(note: Note) = View.OnClickListener {
        val action = MainFragmentDirections.actionMainFragmentToEditFragment(note)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAccountImage(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            Glide.with(requireContext()).load(currentUser.photoUrl).into(binding.account)
        } else {
            Glide.with(requireContext()).load(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_account_circle_24)).into(binding.account)
        }
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
