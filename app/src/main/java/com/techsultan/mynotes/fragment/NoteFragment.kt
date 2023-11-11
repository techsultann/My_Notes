package com.techsultan.mynotes.fragment

import android.app.Application
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.techsultan.mynotes.MainActivity
import com.techsultan.mynotes.NoteApplication
import com.techsultan.mynotes.R
import com.techsultan.mynotes.adapter.NoteAdapter
import com.techsultan.mynotes.databinding.FragmentNoteBinding
import com.techsultan.mynotes.models.Note
import com.techsultan.mynotes.viewmodel.NoteViewModel
import com.techsultan.mynotes.viewmodel.NoteViewModelFactory


class NoteFragment : Fragment(), MenuProvider {

    private var _binding : FragmentNoteBinding? = null
    private val binding get() =_binding!!
    private lateinit var noteAdapter: NoteAdapter
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((requireActivity().application as NoteApplication).repository)
    }


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


        setupRecyclerview()
        setOfSwipeToDelete()

        binding.extendedFab.setOnClickListener {
            val newNote = Note(id = 0, noteTitle = "Note", note = "", date = "")
            findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToNewNoteFragment(newNote))

        }
    }



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
        noteViewModel.getAllNote().observe(viewLifecycleOwner){ notes ->
            Log.d("Notes", "Note List: $notes")
            noteAdapter.differ.submitList(notes)

        }

        }

    private fun searchNote() {

    }

    private fun setOfSwipeToDelete() {

        // Creating a swipe to delete method for the Rv
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                // This is called when we swipe our item to right direction
                // and also to get the position of item
                val position = viewHolder.adapterPosition
                val note = noteAdapter.differ.currentList[position]

                // Remove or delete the item from Rv and Database
                noteViewModel.deleteNote(note)

                //This displays the snack bar action
                Snackbar.make(view!!, "Note deleted successfully", Snackbar.LENGTH_SHORT).apply {
                    setAction("UNDO") {
                        noteViewModel.addNote(note)
                    }
                    show()
                }

            }
        }

        //This attach the item touch helper to the recycler view
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.app_bar_search -> {

            }
        }
        return true
    }


}