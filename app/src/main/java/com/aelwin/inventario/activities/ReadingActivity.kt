package com.aelwin.inventario.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.aelwin.inventario.adapters.ReadingAdapter
import com.aelwin.inventario.api.ConsumeInventarioApi
import com.aelwin.inventario.databinding.ActivityReadingBinding
import com.aelwin.inventario.util.Constantes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadingBinding
    private lateinit var readingAdapter: ReadingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initListeners()
        getReadingInformation(intent.getIntExtra(Constantes.BOOK_ID, 0))
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
        binding.fabAddReading.setOnClickListener { showAddAuthorDialog() }
    }

    private fun initUI() {
        readingAdapter = ReadingAdapter()
        binding.rvReading.setHasFixedSize(true)
        binding.rvReading.layoutManager = LinearLayoutManager(this)
        binding.rvReading.adapter = readingAdapter
    }

    private fun showAddAuthorDialog() {
        TODO("Not yet implemented")
    }
}