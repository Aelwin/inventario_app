package com.aelwin.inventario.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.aelwin.inventario.adapters.AuthorAdapter
import com.aelwin.inventario.api.Author
import com.aelwin.inventario.api.AuthorCreate
import com.aelwin.inventario.api.ConsumeInventarioApi
import com.aelwin.inventario.databinding.ActivityAuthorBinding
import com.aelwin.inventario.databinding.DialogCreateAuthorBinding
import com.aelwin.inventario.util.Constantes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorBinding
    private lateinit var authorAdapter: AuthorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        initUI()
    }

    private fun initListeners() {
        binding.fabAddAuthor.setOnClickListener { showAddAuthorDialog() }
    }

    private fun showAddAuthorDialog() {
        val dialog = Dialog(this)
        val dialogBinding: DialogCreateAuthorBinding = DialogCreateAuthorBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        val btnAddAuthor: Button = dialogBinding.btAddAuthor
        val edAuthorName: EditText = dialogBinding.edAuthorName
        btnAddAuthor.setOnClickListener {
            val newAuthorName = edAuthorName.text.toString()
            if (newAuthorName.isNotEmpty() && newAuthorName.isNotBlank()) {
                val newAuthor: AuthorCreate = AuthorCreate(nombre = newAuthorName)
                dialog.hide()
                binding.progressBar.isVisible = true
                CoroutineScope(Dispatchers.IO).launch {// Este código es asíncrono
                    ConsumeInventarioApi.createAuthor(newAuthor)
                    val authors = ConsumeInventarioApi.getAuthors()
                    runOnUiThread {
                        authorAdapter.updateList(authors)
                        binding.progressBar.isVisible = false // Realmente se va a poner en 'gone'
                    }
                }
            }
        }
        dialog.show()
    }

    private fun initUI() {
        binding.svAuthor.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })
        authorAdapter = AuthorAdapter() { navigateToDetail(it) }
        binding.rvAuthor.setHasFixedSize(true)
        binding.rvAuthor.layoutManager = LinearLayoutManager(this)
        binding.rvAuthor.adapter = authorAdapter
        loadInitAuthors()
    }

    private fun loadInitAuthors() {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {// Este código es asíncrono
            val authors = ConsumeInventarioApi.getAuthors()
            runOnUiThread {
                authorAdapter.updateList(authors)
                binding.progressBar.isVisible = false // Realmente se va a poner en 'gone'
            }
        }
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {// Este código es asíncrono
            val authors = ConsumeInventarioApi.getAuthors(query)
            runOnUiThread {
                authorAdapter.updateList(authors)
                binding.progressBar.isVisible = false // Realmente se va a poner en 'gone'
            }
        }
    }

    private fun navigateToDetail(id: Int) {
        val intent = Intent(this, AuthorDetailActivity::class.java)
        intent.putExtra(Constantes.AUTHOR_ID, id)
        startActivity(intent)
    }
}