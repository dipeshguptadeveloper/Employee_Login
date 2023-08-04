package com.dkgtech.notefirebasemvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dkgtech.notefirebasemvvm.databinding.NoteRowBinding
import com.dkgtech.notefirebasemvvm.model.NoteModel

class RecyclerNoteAdapter(val context: Context, val arrNote: ArrayList<NoteModel>) :
    RecyclerView.Adapter<RecyclerNoteAdapter.ViewHolder>() {
    class ViewHolder(val binding: NoteRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NoteRowBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return arrNote.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            txtIndex.text = "${position + 1}"
            txtTitle.text = arrNote[position].title
            txtDesc.text = arrNote[position].desc
        }
    }
}