package com.aelwin.inventario.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.aelwin.inventario.api.Reading
import com.aelwin.inventario.databinding.ItemReadingListBinding


class ReadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemReadingListBinding.bind(view)

    fun render(reading: Reading, onLongClickListener: (Int) -> Unit) {
        binding.tvReader.text = reading.lector
        binding.tvStartDate.text = reading.fechaInicioFormateada()
        binding.tvEndDate.text = reading.fechaFinFormateada()
        binding.rbLectura.rating = reading.valoracion.toFloat()
        binding.root.setOnLongClickListener {
            onLongClickListener(reading.id)
            true
        }
    }
}