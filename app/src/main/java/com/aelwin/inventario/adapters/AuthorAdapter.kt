package com.aelwin.inventario.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aelwin.inventario.R
import com.aelwin.inventario.api.Author
import com.aelwin.inventario.holders.AuthorViewHolder

class AuthorAdapter(var authors: List<Author> = emptyList(), private val onItemSelected: (Int) -> Unit): RecyclerView.Adapter<AuthorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_author_list, parent, false)
        return AuthorViewHolder(view)
    }

    override fun getItemCount() = authors.size

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        holder.render(authors[position], onItemSelected)
    }

    fun updateList(authors: List<Author>) {
        this.authors = authors
        notifyDataSetChanged()
    }
}