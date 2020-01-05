package com.github.ymatoi.note.ui

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import com.airbnb.epoxy.Typed2EpoxyController
import com.github.ymatoi.note.NoteBindingModel_
import com.github.ymatoi.note.NotesHeaderBindingModel_
import com.github.ymatoi.note.R
import com.github.ymatoi.note.database.Note

class NotesController(private val listener: Listener) : Typed2EpoxyController<String, List<Note>>() {
    interface Listener {
        fun onNoteClick(note: Note)
        fun onNextClick()
        fun onPrevClick()
        fun onTitleClick()
    }

    override fun buildModels(dateText: String, data: List<Note>?) {
        data ?: return

        NotesHeaderBindingModel_()
            .title(dateText)
            .id("date_title")
            .onBind { model, view, position ->
                val next = view.dataBinding.root.findViewById<AppCompatImageButton>(R.id.next_button)
                next.setOnClickListener {
                    listener.onNextClick()
                }
                val prev = view.dataBinding.root.findViewById<AppCompatImageButton>(R.id.prev_button)
                prev.setOnClickListener {
                    listener.onPrevClick()
                }
                val title = view.dataBinding.root.findViewById<TextView>(R.id.title)
                title.setOnClickListener {
                    listener.onTitleClick()
                }
            }
            .addTo(this)

        data.forEach { note ->
            NoteBindingModel_()
                .createdAt(note.dateTimeText)
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
