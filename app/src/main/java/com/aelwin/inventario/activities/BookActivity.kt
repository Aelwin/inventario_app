package com.aelwin.inventario.activities


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.aelwin.inventario.R
import com.aelwin.inventario.api.AuthorBook
import com.aelwin.inventario.api.BookCreate
import com.aelwin.inventario.api.ConsumeInventarioApi
import com.aelwin.inventario.databinding.ActivityBookBinding
import com.aelwin.inventario.fragments.DatePickerFragment
import com.aelwin.inventario.util.Constantes
import com.aelwin.inventario.util.Utilidades
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

        binding.rgLanguage1.setOnCheckedChangeListener(listener1)
        binding.rgLanguage2.setOnCheckedChangeListener(listener2)

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            binding.etPurchaseDate.setText(selectedDate)
        }
        binding.etPurchaseDate.setOnClickListener {
            val datePickerFragment = DatePickerFragment(dateSetListener)
            datePickerFragment.show(supportFragmentManager, "datePicker")
        }
    }

    private fun newBook() {
        if (isRequiredFieldsFilled()) {
            val book: BookCreate = BookCreate.Builder()
                .withTitulo(binding.etTitle.text.toString().takeIf { it.isNotEmpty() })
                .withPropietario(binding.etOwner.text.toString().takeIf { it.isNotEmpty() })
                .withAutores(listOf(AuthorBook(authorID)))
                .withPrecio(binding.etPrice.text.toString().takeIf { it.isNotEmpty() }?.toFloat())
                .withIsbn(binding.etISBN.text.toString().takeIf { it.isNotEmpty() })
                .withSinopsis(binding.etSynopsis.text.toString().takeIf { it.isNotEmpty() })
                .withEditorial(binding.etPublisher.text.toString().takeIf { it.isNotEmpty() })
                .withFechaCompra(binding.etPurchaseDate.text.toString().takeIf { it.isNotEmpty() }
                    ?.let { Utilidades.getApiDateFromStringOrNull(it) })
                .withObservaciones(binding.etComments.text.toString().takeIf { it.isNotEmpty() })
                .withCategoria(binding.etCategory.text.toString().takeIf { it.isNotEmpty() })
                .withSaga(binding.etSaga.text.toString().takeIf { it.isNotEmpty() })
                .withFormato(getCheckedFormat())
                .withIdioma(getCheckedLanguage())
                .build()
            CoroutineScope(Dispatchers.IO).launch {
                ConsumeInventarioApi.createBook(book)
                navigateToAuthorDetail()
            }
        }
    }

    private fun isRequiredFieldsFilled(): Boolean {
        var fieldsFilled = true
        if (binding.etTitle.text.isNullOrEmpty()) {
            binding.tilTitle.error = getString(R.string.required)
            fieldsFilled = false
        } else {
            binding.tilTitle.error = null
            binding.tilTitle.boxStrokeWidth = 0
        }
        if (binding.etOwner.text.isNullOrEmpty()) {
            binding.tilOwner.error = getString(R.string.required)
            fieldsFilled = false
        } else {
            binding.tilOwner.error = null
            binding.tilOwner.boxStrokeWidth = 0
        }

        return fieldsFilled
    }

    private fun navigateToAuthorDetail() {
        getCheckedLanguage()
        val intent = Intent(this, AuthorDetailActivity::class.java)
        intent.putExtra(Constantes.AUTHOR_ID, authorID)
        startActivity(intent)
    }

    private fun getCheckedLanguage(): String {
        val chkId1: Int = binding.rgLanguage1.checkedRadioButtonId
        val chkId2: Int = binding.rgLanguage2.checkedRadioButtonId
        val realCheck = if (chkId1 == -1) chkId2 else chkId1
        val rb = findViewById<View>(realCheck) as RadioButton
        return rb.text.toString()
    }

    private fun getCheckedFormat(): String {
        val rb = findViewById<View>(binding.rgFormat.checkedRadioButtonId) as RadioButton
        return rb.text.toString()
    }

    private val listener1: OnCheckedChangeListener = OnCheckedChangeListener { _, checkedId ->
        if (checkedId != -1) {
            binding.rgLanguage2.setOnCheckedChangeListener(null) // remove the listener before clearing so we don't throw that stackoverflow exception
            binding.rgLanguage2.clearCheck() // clear the other RadioGroup!
            binding.rgLanguage2.setOnCheckedChangeListener(listener2) //reset the listener
        }
    }

    private val listener2: OnCheckedChangeListener = OnCheckedChangeListener { _, checkedId ->
        if (checkedId != -1) {
            binding.rgLanguage1.setOnCheckedChangeListener(null)
            binding.rgLanguage1.clearCheck()
            binding.rgLanguage1.setOnCheckedChangeListener(listener1)
        }
    }
}