package com.aelwin.inventario.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.aelwin.inventario.api.Book
import com.aelwin.inventario.databinding.ItemBookListBinding

class BookFromAuthorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemBookListBinding.bind(view)

    fun render(book: Book, onItemSelected: (Int) -> Unit) {
        binding.tvTitle.text = book.title
        binding.root.setOnClickListener { onItemSelected(book.id) }
    }
}