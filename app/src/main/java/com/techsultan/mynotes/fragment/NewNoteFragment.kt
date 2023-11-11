package com.techsultan.mynotes.fragment

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkManager
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.google.android.material.snackbar.Snackbar
import com.techsultan.mynotes.NoteApplication
import com.techsultan.mynotes.R
import com.techsultan.mynotes.databinding.FragmentNewNoteBinding
import com.techsultan.mynotes.models.Note
import com.techsultan.mynotes.repository.NoteRepositoryImpl
import com.techsultan.mynotes.viewmodel.NoteViewModel
import com.techsultan.mynotes.viewmodel.NoteViewModelFactory
import com.thebluealliance.spectrum.internal.ColorUtil
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class NewNoteFragment : Fragment() {

    private var _binding : FragmentNewNoteBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel : NoteViewModel by viewModels {
        val application = activity?.applicationContext
        NoteViewModelFactory((application as NoteApplication).repository)
    }
    private val safeArgs : NewNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentNote = safeArgs.note

        updateNote()

        binding.saveBtn.setOnClickListener {
            saveNote()
        }


        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = currentTime.format(formatter).toString()
        binding.dateTv!!.text = formatted

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

            binding.colorFabBtn.setOnClickListener {
                var mMaterialColor = ""
                MaterialColorPickerDialog
                    .Builder(requireActivity())
                    .setColorSwatch(ColorSwatch._500)
                    .setDefaultColor(mMaterialColor)
                    .setColorListener(object : ColorListener {
                        override fun onColorSelected(color: Int, colorHex: String) {
                            mMaterialColor = colorHex
                            setTextFieldBackground(binding.etNote, color)
                        }
                    })
                    .setDismissListener {
                        Log.d("MaterialDialogPicker", "Handle Dismiss Event")
                    }
                    .show()
            }
        } else {

            binding.colorFabBtn.setOnClickListener {
                MaterialColorPickerDialog
                    .Builder(requireContext())
                    .setColorSwatch(ColorSwatch.A200)
                    .setDefaultColor("")
                    .setColorListener( object : ColorListener {
                        override fun onColorSelected(color: Int, colorHex: String) {
                            TODO("Not yet implemented")
                        }
                    })
                    .setDismissListener{
                        Log.d("Material bottom sheet", "Handle dismiss event")
                    }
                    .showBottomSheet(childFragmentManager)
            }

        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initializeNewNote() {

        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = currentTime.format(formatter).toString()
         binding.noteTitleEt.text?.clear()
         binding.etNote.text?.clear()
         binding.dateTv?.text = formatted
    }

    fun editNote () {

    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveNote() {

        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = currentTime.format(formatter).toString()
        val noteTitle = binding.noteTitleEt.text.toString().trim()
        val noteBody = binding.etNote.text.toString().trim()
       // val dateTime = formatted

        if (currentNote.id == 0) {
            val saveNote = Note(0, noteTitle, noteBody, formatted)

            noteViewModel.addNote(saveNote)
            Snackbar.make(
                requireView(),
                "Note saved Successfully",
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            val updateNote = Note(currentNote.id, noteTitle, noteBody, formatted)
            noteViewModel.updateNote(updateNote)
            Snackbar.make(
                requireView(),
                "Note saved Successfully",
                Snackbar.LENGTH_SHORT
            ).show()

        }


        findNavController().popBackStack()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateNote() {

        currentNote = safeArgs.note

        binding.noteTitleEt.setText(currentNote.noteTitle)
        binding.dateTv!!.text = currentNote.date
        binding.etNote.setText(currentNote.note)

        /*binding.saveBtn.setOnClickListener {

            val noteTitle = binding.noteTitleEt.text.toString().trim()
            val noteBody = binding.etNote.text.toString().trim()
            val currentTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = currentTime.format(formatter).toString()

        }*/

    }

    private fun setTextFieldBackground(textField: AppCompatEditText, color: Int) {
        if (ColorUtil.isColorDark(color)) {
            textField.setTextColor(Color.WHITE)
        } else {
            textField.setTextColor(Color.BLACK)
        }
        textField.backgroundTintList = ColorStateList.valueOf(color)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}