package com.github.ymatoi.note.ui

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.note

class NotesController(private val listener: Listener) : EpoxyController() {
    interface Listener {
        fun onNoteClick(note: Note): View.OnClickListener
    }

    var notes: List<Note> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        notes.forEach { note ->
            note {
                id(note.uuid)
                createdAt(note.dateTimeText)
                text(note.text)
                onClick(listener.onNoteClick(note))
            }
        }
    }
}
