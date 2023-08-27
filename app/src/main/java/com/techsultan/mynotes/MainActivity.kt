package com.techsultan.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.techsultan.mynotes.databinding.ActivityMainBinding
import com.techsultan.mynotes.viewmodel.NoteViewModel
import com.techsultan.mynotes.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = NoteViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)
    }
}