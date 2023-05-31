package com.aelwin.inventario.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aelwin.inventario.R
import com.aelwin.inventario.api.Reading
import com.aelwin.inventario.holders.ReadingViewHolder

class ReadingAdapter(var readings: List<Reading> = emptyList(), private val onLongClickListener: (Int) -> Unit): RecyclerView.Adapter<ReadingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reading_list, parent, false)
        return ReadingViewHolder(view)
    }

    override fun getItemCount() = readings.size

    override fun onBindViewHolder(holder: ReadingViewHolder, position: Int) {
        holder.render(readings[position], onLongClickListener)
    }

    fun updateList(readings: List<Reading>) {
        this.readings = readings
        notifyDataSetChanged()
    }
}