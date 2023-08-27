package com.techsultan.mynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.techsultan.mynotes.models.Note

class NoteViewModel(
    private val app: Application
) : AndroidViewModel(app) {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    init {
        _notes.value = listOf(

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
    }


}