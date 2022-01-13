package com.smartu.notes.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartu.notes.R
import com.smartu.notes.activities.NotesActivity
import com.smartu.notes.models.Note
import com.smartu.notes.models.NoteListViewModel
import java.util.*

private const val TAG="NoteFragment"
private const val ARG_USER_ID="user_id"
class NotesFragment :Fragment() {

    lateinit var userID:UUID
    private val noteListViewModel:NoteListViewModel by lazy {
        ViewModelProviders.of(this).get(NoteListViewModel::class.java)
    }

    private lateinit var notesRecyclerView:RecyclerView
    private var adapter:NoteAdapter?=NoteAdapter(emptyList())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        userID=arguments?.getSerializable(ARG_USER_ID) as UUID
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_notes,container,false)
        notesRecyclerView=view?.findViewById(R.id.notes_recycler_view) as RecyclerView
        notesRecyclerView.layoutManager=LinearLayoutManager(context)
        notesRecyclerView.adapter=adapter

        
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteListViewModel.getNoteListLiveData(userID).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            notes->
            notes?.let {
                updateUI(notes)
            }
        })
    }


    private inner class NoteHolder(view:View) :RecyclerView.ViewHolder(view), View.OnClickListener{
        lateinit var note:Note

        init {
            itemView.setOnClickListener(this)
        }

        private val titleTextView:TextView = itemView.findViewById(R.id.note_title)
        private val noteTextView:TextView=itemView.findViewById(R.id.note_text)

        fun onBind(note:Note)
        {
            this.note=note
            titleTextView.text=this.note.title
            noteTextView.text=this.note.noteBody
        }

        override fun onClick(p0: View?) {
            Toast.makeText(context,"CLICKED",Toast.LENGTH_SHORT).show()
        }
    }
    private inner class NoteAdapter(var notes:List<Note>):RecyclerView.Adapter<NoteHolder>(){


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
            val view=layoutInflater.inflate(R.layout.list_item_note,parent,false)
            return NoteHolder(view)
        }

        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            val note=notes[position]
            holder.onBind(note)
        }

        override fun getItemCount(): Int {
            return notes.size
        }

    }


    fun updateUI(notes:List<Note>){
        adapter=NoteAdapter(notes)
        notesRecyclerView.adapter=adapter
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_fragment_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    companion object{
        fun newInstance(userID:UUID):NotesFragment{
            val args=Bundle().apply {
                putSerializable(ARG_USER_ID,userID)
            }
            return NotesFragment().apply {
                arguments=args
            }
        }
    }
}