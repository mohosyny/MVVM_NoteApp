package neo.mohosyny.mynote.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item.view.*
import neo.mohosyny.mynote.R
import neo.mohosyny.mynote.database.Note

class AdapterMain(var onClickListener: (Int) -> Unit) :
    ListAdapter<Note, AdapterMain.NoteViewHolder>(DIFFCALBACK) {


    companion object DIFFCALBACK : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.priority == newItem.priority
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.txtTitle.text = getItem(position).title
        holder.txtDesc.text = getItem(position).description
        holder.txtPriority.text = getItem(position).priority.toString()
    }


    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle: TextView = itemView.txt_title
        var txtDesc: TextView = itemView.txt_desc
        var txtPriority: TextView = itemView.txt_priority

        init {
            itemView.setOnClickListener { onClickListener(adapterPosition) }
        }
    }


    fun getNoteAt(position: Int): Note {
        return getItem(position)
    }

}


