package com.aelwin.inventario.activities


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aelwin.inventario.R
import com.aelwin.inventario.api.BookCreate
import com.aelwin.inventario.api.ConsumeInventarioApi
import com.aelwin.inventario.databinding.ActivityBookBinding
import com.aelwin.inventario.util.Constantes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookBinding
    private var authorID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authorID = intent.getIntExtra(Constantes.AUTHOR_ID, 0)
        initListeners()
    }

    private fun initListeners() {
        binding.btClose.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
        }
        binding.btNew.setOnClickListener { newBook() }
    }

    private fun newBook() {
        val book: BookCreate = BookCreate.Builder()
            .withEditorial("")
            .build()
        CoroutineScope(Dispatchers.IO).launch {
            ConsumeInventarioApi.createBook(book)
            navigateToAuthorDetail()
        }
    }

    private fun navigateToAuthorDetail() {
        val intent = Intent(this, AuthorDetailActivity::class.java)
        intent.putExtra(Constantes.AUTHOR_ID, authorID)
        startActivity(intent)
    }
}