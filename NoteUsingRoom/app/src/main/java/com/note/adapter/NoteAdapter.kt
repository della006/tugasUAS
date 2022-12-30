package com.note.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.note.R
import com.note.databinding.ListItemsBinding
import com.note.entity.Note
import com.note.method.DateChange

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private val dataNotes = ArrayList<Note>()
    private lateinit var listener: NoteListener

    fun setOnClicked(listener: NoteListener) {
        this.listener = listener
    }

    fun setData(items: ArrayList<Note>) {
        dataNotes.clear()
        dataNotes.addAll(items)
        notifyDataSetChanged()
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ListItemsBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val dateChange = DateChange()
        val item = dataNotes[position]
        with(holder) {
            binding.textViewTitle.text = item.title
            binding.tvDate.text = dateChange.changeFormatDate(item.date)
            binding.textViewBody.text = item.body
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataNotes.size
    }

    interface NoteListener {
        fun onItemClicked(note: Note)
    }
}