package com.github.ymatoi.note.ui.main

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.github.ymatoi.note.DateTextBindingModel_
import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.dateText
import com.github.ymatoi.note.note
import com.github.ymatoi.note.util.dateText

class NotesController(private val listener: Listener) : TypedEpoxyController<List<Note>?>() {
    interface Listener {
        fun onNoteClick(note: Note): View.OnClickListener
    }

    override fun buildModels(notes: List<Note>?) {
        val groupedNotes = notes?.groupBy { it.recordedAt.dateText() }
        groupedNotes?.forEach { (key, notes) ->
            dateText {
                id(key)
                dateText(key)
            }

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

    override fun isStickyHeader(position: Int): Boolean {
        return adapter.getModelAtPosition(position) is DateTextBindingModel_
    }
}
