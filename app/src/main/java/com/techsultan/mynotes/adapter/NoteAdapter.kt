package com.techsultan.mynotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.techsultan.mynotes.databinding.NoteItemsBinding
import com.techsultan.mynotes.fragment.NoteFragmentDirections
import com.techsultan.mynotes.models.Note

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: NoteItemsBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallBack = object : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        return NoteViewHolder(
            NoteItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.binding.apply {

            noteTitleTv.text = currentNote.noteTitle
            textView.text = currentNote.note
            dateTextView.text = currentNote.date

        }

        holder.itemView.setOnClickListener {
            val directions = NoteFragmentDirections.actionNoteFragmentToNewNoteFragment(currentNote)
            it.findNavController().navigate(directions)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}