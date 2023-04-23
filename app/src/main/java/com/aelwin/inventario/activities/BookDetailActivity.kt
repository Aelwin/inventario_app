package com.aelwin.inventario.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aelwin.inventario.R
import com.aelwin.inventario.api.BookDetail
import com.aelwin.inventario.api.ConsumeInventarioApi
import com.aelwin.inventario.api.Reading
import com.aelwin.inventario.api.isbn.ConsumeIsbnApi
import com.aelwin.inventario.databinding.ActivityBookDetailBinding
import com.aelwin.inventario.util.Constantes
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBookInformation(intent.getIntExtra(Constantes.BOOK_ID, 0))
    }

    private fun getBookInformation(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val book = ConsumeInventarioApi.getBook(id)
            if (book != null) {
                runOnUiThread { createUI(book) }
            } else {
                Log.e("Annatar", "Error al recuperar el libro con id $id")
            }
        }
    }

    private fun createUI(book: BookDetail) {
        binding.tvTitle.text = book.title
        binding.rbLectura.rating = book.getRatingOrDefault().toFloat()
        binding.tvAuthor.text = book.authorsNames()
        binding.tvCategoria.text = book.categoria
        binding.tvISBN.text = book.isbn
        binding.tvSaga.text = book.saga
        binding.tvPurchaseDate.text = book.formattedPurchaseDate()
        binding.tvPrice.text = book.precio.toString()
        binding.tvPublisher.text = book.editorial
        binding.tvOwner.text = book.propietario
        binding.btnShowReadings.setOnClickListener { nagivateToShowReadings(book.lecturas) }
        CoroutineScope((Dispatchers.IO)).launch {
            val imageUrl = ConsumeIsbnApi.getImageUrl(book.isbn)
            runOnUiThread {
                Picasso.get().load(imageUrl)
                    .error(R.drawable.ic_image_not_found)
                    .into(binding.ivBook)
            }
        }
    }

    private fun nagivateToShowReadings(lecturas: List<Reading>) {
        val intent = Intent(this, AuthorActivity::class.java)
        //intent.putExtra(Constantes.AUTHOR_ID, id)
        startActivity(intent)

    }
}