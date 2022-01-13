package com.smartu.notes.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartu.notes.R
import com.smartu.notes.Repository
import com.smartu.notes.models.Note
import com.smartu.notes.models.NoteListViewModel
import java.util.*

private const val TAG="NoteFragment"
private const val ARG_USER_ID="user_id"
class NotesFragment :Fragment() {
    private val addNoteUUID:UUID=UUID.fromString("2a0635e1-8d77-41c2-bf64-9b272314fb90") //default uuid for adding a note
    lateinit var userID:UUID

    private val noteListViewModel:NoteListViewModel by lazy {
        ViewModelProviders.of(this).get(NoteListViewModel::class.java) //initialize notelistviewmodel
    }

    private lateinit var notesRecyclerView:RecyclerView
    private var adapter:NoteAdapter?=NoteAdapter(emptyList())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // for menu inflation
        userID=arguments?.getSerializable(ARG_USER_ID) as UUID //get user id from fragment arguments on create


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

        //get list of notes from db and use them in recycler view
        noteListViewModel.getNoteListLiveData(userID).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            notes->
            notes?.let {
                updateUI(notes)
            }
        })
    }

    //view holder holds note title,text and delete button in recycler view
    private inner class NoteHolder(view:View) :RecyclerView.ViewHolder(view), View.OnClickListener{
        lateinit var note:Note

        init {
            itemView.setOnClickListener(this)
        }

        private val titleTextView:TextView = itemView.findViewById(R.id.note_title)
        private val noteTextView:TextView=itemView.findViewById(R.id.note_text)
        private val deleteBtn:Button=itemView.findViewById(R.id.delete_btn)

        fun onBind(note:Note)
        {
            this.note=note
            titleTextView.text=this.note.title
            noteTextView.text=this.note.noteBody

            deleteBtn.setOnClickListener {
                noteListViewModel.deleteNote(note)
            }

        }

        override fun onClick(p0: View?) {
            val fragment=EditorFragment.newInstance(note.id)
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container,fragment).addToBackStack("tagtag")
                commit()
            }


        }
    }
    private inner class NoteAdapter(var notes:List<Note>):RecyclerView.Adapter<NoteHolder>(){ //adapter creates and bind list of viewholder to recycler view


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
            val view=layoutInflater.inflate(R.layout.list_item_note,parent,false)
            return NoteHolder(view)
        }

        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            val note=notes[position]
            holder.onBind(note)
        }

        override fun getItemCount(): Int {
            hideAddANoteButton(notes.size) // hide add note button (i put it here so db has enough time to get list before add note button is shown)
            return notes.size
        }

    }


    fun updateUI(notes:List<Note>){ //update recycler view with changes
        adapter=NoteAdapter(notes)
        notesRecyclerView.adapter=adapter
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) { //inflate menu
        inflater.inflate(R.menu.notes_fragment_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.add_note -> {
                val editorFragment=EditorFragment.newInstance(addNoteUUID)

                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container,editorFragment).addToBackStack("tagtag")
                    commit()
                }

                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun hideAddANoteButton(size:Int){ // show or hide add note button based on size of list of notes
        val addNoteBtn:Button?=getView()?.findViewById(R.id.add_a_note_btn)
        if(size==0){
            addNoteBtn?.visibility=View.VISIBLE
            addNoteBtn?.setOnClickListener {
                val fragment=EditorFragment.newInstance(addNoteUUID)
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container,fragment).addToBackStack("tagtag")
                    commit()
                }
            }
        }
        else{
            addNoteBtn?.visibility=View.INVISIBLE
        }
    }


    companion object{
        fun newInstance(userID:UUID):NotesFragment{ //get userid from notesActivity
            val args=Bundle().apply {
                putSerializable(ARG_USER_ID,userID)
            }
            return NotesFragment().apply {
                arguments=args
            }
        }
    }
}