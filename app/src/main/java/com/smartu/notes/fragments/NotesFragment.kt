package com.smartu.notes.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartu.notes.R
import com.smartu.notes.models.Note
import com.smartu.notes.models.NoteListViewModel

class NotesFragment :Fragment() {

    private val noteListViewModel: NoteListViewModel by lazy {
        ViewModelProviders.of(this).get(NoteListViewModel::class.java)
    }

    private lateinit var notesRecyclerView:RecyclerView
    private var adapter:NoteAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TESTLOG", "Total crimes: ${noteListViewModel.notes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_notes,container,false)
        notesRecyclerView=view?.findViewById(R.id.notes_recycler_view) as RecyclerView
        notesRecyclerView.layoutManager=LinearLayoutManager(context)

        updateUI()
        
        return view
    }


    private inner class NoteHolder(view:View) :RecyclerView.ViewHolder(view){
        val titleTextView:TextView = itemView.findViewById(R.id.note_title)
        val noteTextView:TextView=itemView.findViewById(R.id.note_text)


    }
    private inner class NoteAdapter(var notes:List<Note>):RecyclerView.Adapter<NoteHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
            val view=layoutInflater.inflate(R.layout.list_item_note,parent,false)
            return NoteHolder(view)
        }

        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            val note=notes[position]
            holder.apply {
                titleTextView.text=note.title
                noteTextView.text=note.noteBody
            }
        }

        override fun getItemCount(): Int {
            return notes.size
        }

    }


    fun updateUI(){
        val notes= noteListViewModel.notes
        adapter=NoteAdapter(notes)
        notesRecyclerView.adapter=adapter
    }

}