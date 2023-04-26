package com.aelwin.inventario.activities

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.aelwin.inventario.adapters.ReadingAdapter
import com.aelwin.inventario.api.ConsumeInventarioApi
import com.aelwin.inventario.api.ReadingCreate
import com.aelwin.inventario.databinding.ActivityReadingBinding
import com.aelwin.inventario.databinding.DialogCreateReadingBinding
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
        readingAdapter = ReadingAdapter()
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
            val startDate = Utilidades.getStringApiDateFromDatePicker(dialogBinding.dpStartDate)
            if (reader.isNotEmpty() && reader.isNotBlank() && startDate != null) {
                val reading = ReadingCreate(dialogBinding.rbLectura.rating.toInt(),
                    reader, startDate, Utilidades.getStringApiDateFromDatePicker(dialogBinding.dpEndDate), bookID)
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
        dialogBinding.icStartDate.setOnClickListener {
            dialogBinding.dpStartDate.isVisible = true
        }
        dialogBinding.icEndDate.setOnClickListener {
            dialogBinding.dpEndDate.isVisible = true
        }
        dialogBinding.dpStartDate.setOnDateChangedListener { _, _, _, _ ->
            dialogBinding.dpStartDate.isVisible = false
            dialogBinding.tvStartDate.text = Utilidades.getStringShowDateFromDatePicker(dialogBinding.dpStartDate)
        }
        dialogBinding.dpEndDate.setOnDateChangedListener { _, _, _, _ ->
            dialogBinding.dpEndDate.isVisible = false
            dialogBinding.tvEndDate.text = Utilidades.getStringShowDateFromDatePicker(dialogBinding.dpEndDate)
        }
        dialog.show()
    }

}