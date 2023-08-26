package com.techsultan.mynotes.fragment

import android.app.Application
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.techsultan.mynotes.MainActivity
import com.techsultan.mynotes.NoteApplication
import com.techsultan.mynotes.R
import com.techsultan.mynotes.adapter.NoteAdapter
import com.techsultan.mynotes.databinding.FragmentNoteBinding
import com.techsultan.mynotes.models.Note
import com.techsultan.mynotes.viewmodel.NoteViewModel
import com.techsultan.mynotes.viewmodel.NoteViewModelFactory


class NoteFragment : Fragment() {

    private var _binding : FragmentNoteBinding? = null
    private val binding get() =_binding!!
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteViewModel: NoteViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        noteViewModel = (activity as MainActivity).viewModel

        setupRecyclerview()
        binding.extendedFab.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_newNoteFragment)
        }
    }


    private val dummyNotes = listOf(

        Note(
            1,
            "App Compat Activity",
            "AppCompatActivity is a base class that we can extend for using newer platform features on older Android devices. Some of these backported features include the usage of the action bar, including action items, etc. It can switch between light and dark themes by using default androidx. appcompat themes",
            "8-24-2023"
        ),

        Note(
            2,
            "Spinners",
            "Spinners provide a quick way to select one value from a set. In the default state, a spinner shows its currently selected value. Touching the spinner displays a dropdown menu with all other available values, from which the user can select a new one.",
            "8-24-2022"
        ),

        Note(
            3,
            "Checkboxes",
            "Checkboxes let users select one or more items from a list, or turn an item on or off",
            "5-3-2020"
        )
    )

    private fun setupRecyclerview() {
        val recyclerView = binding.recyclerView
        noteAdapter = NoteAdapter()
        recyclerView.adapter = noteAdapter

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

            recyclerView.layoutManager = LinearLayoutManager(activity)
        } else {

            recyclerView.layoutManager = GridLayoutManager(activity, 2)
        }
        // Add the list of notes in the view model to the recycler view
        noteViewModel.notes.observe(viewLifecycleOwner){ notes ->
            noteAdapter.differ.submitList(notes)

        }





        }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}