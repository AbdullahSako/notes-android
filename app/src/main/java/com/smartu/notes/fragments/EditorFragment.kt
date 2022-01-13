package com.smartu.notes.fragments

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.smartu.notes.R
import com.smartu.notes.Repository
import com.smartu.notes.UserPrefrences
import com.smartu.notes.models.Note
import java.util.*

private const val ARGS_NOTE_ID="NoteId"
class EditorFragment :Fragment(R.layout.fragment_editor) {
    private val repo: Repository = Repository.get()
    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editor: EditText = view.findViewById(R.id.editor)
        val title: EditText = view.findViewById(R.id.editor_note_title)
        val saveBtn: Button = view.findViewById(R.id.save_btn)
        val noteID: UUID = arguments?.getSerializable(ARGS_NOTE_ID) as UUID



            repo.getNote(noteID).observe(viewLifecycleOwner, androidx.lifecycle.Observer { // get note info based on note id and show them on screen
                if (it != null) {
                    note = it
                    editor.setText(note.noteBody)
                    title.setText(note.title)
                }

            })

            saveBtn.setOnClickListener {
                if(title.text.isEmpty()){
                    Toast.makeText(activity as Context,R.string.title_validation,Toast.LENGTH_LONG).show()
                    title.background=resources.getDrawable(R.drawable.rounded_corner_red)
                }
                else {
                    if (noteID.toString() == "2a0635e1-8d77-41c2-bf64-9b272314fb90") {
                        val note = Note(
                            userId = UUID.fromString(UserPrefrences.getUserID(activity as Context)),
                            title = title.text.toString(),
                            noteBody = editor.text.toString()
                        )
                        repo.addNote(note) // add note to db
                        parentFragmentManager.popBackStack()
                    } else {
                        note.title = title.text.toString()
                        note.noteBody = editor.text.toString()
                        repo.updateNote(note) //update note in db
                        parentFragmentManager.popBackStack()
                    }
                }


            }

        title.setOnFocusChangeListener { view, b -> //change title field border on focus in case it was turned to red on validation
            if(view.hasFocus()){
                title.background=resources.getDrawable(R.drawable.rounded_corner_white)
            }
        }


    }


    companion object {
        fun newInstance(noteID: UUID): EditorFragment {
            val args = Bundle()
            args.putSerializable(ARGS_NOTE_ID, noteID)
            val fragment = EditorFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
