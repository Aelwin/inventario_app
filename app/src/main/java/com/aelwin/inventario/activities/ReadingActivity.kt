package com.aelwin.inventario.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.aelwin.inventario.R
import com.aelwin.inventario.adapters.ReadingAdapter
import com.aelwin.inventario.api.ConsumeInventarioApi
import com.aelwin.inventario.api.ReadingCreate
import com.aelwin.inventario.databinding.ActivityReadingBinding
import com.aelwin.inventario.databinding.DialogCreateReadingBinding
import com.aelwin.inventario.fragments.DatePickerFragment
import com.aelwin.inventario.util.Constantes
import com.aelwin.inventario.util.Utilidades
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadingBinding
    private lateinit var readingAdapter: ReadingAdapter
    private var bookID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initListeners()
        bookID = intent.getIntExtra(Constantes.BOOK_ID, 0)
        getReadingInformation(bookID)
    }

    private fun getReadingInformation(bookID: Int) {
        binding.pbReading.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val readings = ConsumeInventarioApi.getReadings(bookID)
            runOnUiThread {
                if (readings.isEmpty()) {
                    binding.tvNoResults.isVisible = true
                } else {
                    binding.tvNoResults.isVisible = false
                    readingAdapter.updateList(readings)
                }
                binding.pbReading.isVisible = false
            }
        }
    }

    private fun initListeners() {
        binding.fabAddReading.setOnClickListener { showAddReadingDialog() }
    }

    private fun initUI() {
        readingAdapter = ReadingAdapter { deleteReading(it) }
        binding.rvReading.setHasFixedSize(true)
        binding.rvReading.layoutManager = LinearLayoutManager(this)
        binding.rvReading.adapter = readingAdapter
    }

    private fun showAddReadingDialog() {
        val dialog = Dialog(this)
        val dialogBinding: DialogCreateReadingBinding = DialogCreateReadingBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.btAddReading.setOnClickListener {
            val reader = dialogBinding.etReader.text.toString()
            val startDate = dialogBinding.etStartDate.text.toString().takeIf { it.isNotEmpty() }
                ?.let { Utilidades.getApiDateFromStringOrNull(it) }
            if (reader.isNotEmpty() && reader.isNotBlank() && startDate != null) {
                val endDate = dialogBinding.etEndDate.text.toString().takeIf { it.isNotEmpty() }
                    ?.let { Utilidades.getApiDateFromStringOrNull(it) }
                val reading = ReadingCreate(dialogBinding.rbLectura.rating.toInt(),
                    reader, startDate, endDate, bookID)
                dialog.hide()
                CoroutineScope(Dispatchers.IO).launch {// Este código es asíncrono
                    ConsumeInventarioApi.createReading(reading)
                    val readings = ConsumeInventarioApi.getReadings(bookID)
                    runOnUiThread {
                        binding.tvNoResults.isVisible = false
                        readingAdapter.updateList(readings)
                    }
                }
            }
        }

        val startDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            dialogBinding.etStartDate.setText(selectedDate)
        }
        dialogBinding.etStartDate.setOnClickListener {
            val datePickerFragment = DatePickerFragment(startDateSetListener)
            datePickerFragment.show(supportFragmentManager, "datePicker")
        }
        val endDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            dialogBinding.etEndDate.setText(selectedDate)
        }
        dialogBinding.etEndDate.setOnClickListener {
            val datePickerFragment = DatePickerFragment(endDateSetListener)
            datePickerFragment.show(supportFragmentManager, "datePicker")
        }
        dialog.show()
    }

    private fun deleteReading(id: Int) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_confirm))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    val isDeleted: Boolean = ConsumeInventarioApi.deleteReading(id)
                    if (isDeleted) {
                        val readings = ConsumeInventarioApi.getReadings(bookID)
                        runOnUiThread {
                            binding.tvNoResults.isVisible = readings.isEmpty()
                            readingAdapter.updateList(readings)
                        }
                    }
                    //TODO tratamiento en caso de error al borrar
                }
            }
            .setNegativeButton(getString(R.string.no), null)
            .create()
        dialog.show()
    }

}