package com.github.ymatoi.note.ui

import com.airbnb.epoxy.TypedEpoxyController
import com.github.ymatoi.note.NoteBindingModel_
import com.github.ymatoi.note.database.Note

class NotesController : TypedEpoxyController<List<Note>>() {
    override fun buildModels(data: List<Note>?) {
        data ?: return

        data.forEach { note ->
            NoteBindingModel_()
                .createdAt(note.dateText)
                .text(note.text)
                .id(note.id)
                .addTo(this)
        }
    }
}
