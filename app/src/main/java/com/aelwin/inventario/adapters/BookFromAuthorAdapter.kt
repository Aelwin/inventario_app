package com.aelwin.inventario.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aelwin.inventario.R
import com.aelwin.inventario.api.Author
import com.aelwin.inventario.api.Book
import com.aelwin.inventario.holders.AuthorViewHolder
import com.aelwin.inventario.holders.BookFromAuthorViewHolder

class BookFromAuthorAdapter(var books: List<Book> = emptyList(), private val onItemSelected: (Int) -> Unit): RecyclerView.Adapter<BookFromAuthorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookFromAuthorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_list, parent, false)
        return BookFromAuthorViewHolder(view)
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookFromAuthorViewHolder, position: Int) {
        holder.render(books[position], onItemSelected)
    }

    fun updateList(books: List<Book>) {
        this.books = books
        notifyDataSetChanged()
    }
}