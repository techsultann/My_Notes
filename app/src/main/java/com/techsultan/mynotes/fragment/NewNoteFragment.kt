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
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkManager
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.techsultan.mynotes.R
import com.techsultan.mynotes.databinding.FragmentNewNoteBinding
import com.techsultan.mynotes.viewmodel.NoteViewModel
import com.thebluealliance.spectrum.internal.ColorUtil
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class NewNoteFragment : Fragment() {

    private var _binding : FragmentNewNoteBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel : NoteViewModel by viewModels()


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

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_newNoteFragment_to_noteFragment)
        }

        binding.saveBtn.setOnClickListener {  }

        getCurrentTime()

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

            binding.saveBtn.setOnClickListener {

                noteViewModel.saveNoteInBackground("Note saved").toString()
            }

        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTime() {

        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = currentTime.format(formatter)
        binding.dateTv!!.text = formatted
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