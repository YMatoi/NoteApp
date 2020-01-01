package com.github.ymatoi.note.ui

import com.airbnb.epoxy.TypedEpoxyController
import com.github.ymatoi.note.NoteBindingModel_
import com.github.ymatoi.note.database.Note

class NotesController(private val listener: Listener) : TypedEpoxyController<List<Note>>() {
    interface Listener {
        fun onNoteClick(note: Note)
    }

    override fun buildModels(data: List<Note>?) {
        data ?: return

        data.forEach { note ->
            NoteBindingModel_()
                .createdAt(note.dateText)
                .text(note.text)
                .id(note.id)
                .onBind { model, view, position ->
                    view.dataBinding.root.setOnClickListener {
                        listener.onNoteClick(note)
                    }
                }
                .addTo(this)
        }
    }
}
