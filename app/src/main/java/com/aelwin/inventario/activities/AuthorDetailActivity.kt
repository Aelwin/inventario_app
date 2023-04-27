package com.aelwin.inventario.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.aelwin.inventario.R
import com.aelwin.inventario.adapters.BookFromAuthorAdapter
import com.aelwin.inventario.api.Author
import com.aelwin.inventario.api.ConsumeInventarioApi
import com.aelwin.inventario.databinding.ActivityAuthorDetailBinding
import com.aelwin.inventario.util.Constantes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorDetailBinding
    private lateinit var bookFromAuthorAdapter: BookFromAuthorAdapter
    private var authorID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authorID = intent.getIntExtra(Constantes.AUTHOR_ID, 0)
        bookFromAuthorAdapter = BookFromAuthorAdapter { navigateToDetail(it) }
        binding.rvBook.setHasFixedSize(true)
        binding.rvBook.layoutManager = LinearLayoutManager(this)
        binding.rvBook.adapter = bookFromAuthorAdapter
        initListeners()
        getAuthorInformation()
    }

    private fun initListeners() {
        binding.fabAddBook.setOnClickListener { navigateToAddBook() }
    }

    private fun getAuthorInformation() {
        CoroutineScope(Dispatchers.IO).launch {
            val author = ConsumeInventarioApi.getAuthor(authorID)
            if (author != null) {
                runOnUiThread { createUI(author) }
            } else {
                Log.e("Annatar", "Error al recuperar el autor con id $authorID")
            }
        }
    }

    private fun createUI(author: Author) {
        binding.tvAuthor.text = author.name
        binding.tvBooks.text = getString(R.string.author_books)
        bookFromAuthorAdapter.updateList(author.books)
    }

    private fun navigateToDetail(id: Int) {
        val intent = Intent(this, BookDetailActivity::class.java)
        intent.putExtra(Constantes.BOOK_ID, id)
        startActivity(intent)
    }

    private fun navigateToAddBook() {
        val intent = Intent(this, BookActivity::class.java)
        intent.putExtra(Constantes.AUTHOR_ID, authorID)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }
}