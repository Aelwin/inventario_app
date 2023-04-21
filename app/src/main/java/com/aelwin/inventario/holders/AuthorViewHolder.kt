package com.aelwin.inventario.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.aelwin.inventario.R
import com.aelwin.inventario.api.Author
import com.aelwin.inventario.databinding.ItemAuthorListBinding

class AuthorViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemAuthorListBinding.bind(view)
    private val context = view.context

    fun render(author: Author, onItemSelected: (Int) -> Unit) {
        binding.tvName.text = author.name
        binding.tvBooksNumber.text = context.getString(R.string.author_books_number, author.books.size.toString())
        binding.root.setOnClickListener { onItemSelected(author.id) }
    }
}