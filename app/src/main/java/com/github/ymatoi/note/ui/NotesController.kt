package com.github.ymatoi.note.ui

import com.airbnb.epoxy.EpoxyController
import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.note

class NotesController(private val listener: Listener) : EpoxyController() {
    interface Listener {
        fun onNoteClick(note: Note)
    }

    var notes: List<Note> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }


    override fun buildModels() {
        notes.forEach { note ->
            note {
                id(note.id)
                createdAt(note.dateTimeText)
                text(note.text)
                onBind { _, view, _ ->
                    view.dataBinding.root.setOnClickListener {
                        listener.onNoteClick(note)
                    }
                }
            }
        }
    }
}
