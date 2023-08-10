package com.techsultan.mynotes.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.techsultan.mynotes.R
import com.techsultan.mynotes.databinding.FragmentNoteBinding


class NoteFragment : Fragment() {

    private var _binding : FragmentNoteBinding? = null
    private val binding get() =_binding!!


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

        binding.extendedFab.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_newNoteFragment)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}