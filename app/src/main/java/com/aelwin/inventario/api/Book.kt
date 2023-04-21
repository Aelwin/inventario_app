package com.aelwin.inventario.api

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date

data class Book(@SerializedName("id") val id: Int,
                @SerializedName("titulo") val title: String) {
}

data class BookDetail(@SerializedName("id") val id: Int,
                      @SerializedName("titulo") val title: String,
                      @SerializedName("precio") val precio: Float,
                      @SerializedName("isbn") val isbn: String,
                      @SerializedName("sinopsis") val sinopsis: String,
                      @SerializedName("imagen") val imagen: String,
                      @SerializedName("editorial") val editorial: String,
                      @SerializedName("observaciones") val observaciones: String,
                      @SerializedName("fecha_compra") val fechaCompra: Date,
                      @SerializedName("categoria") val categoria: String,
                      @SerializedName("saga") val saga: String,
                      @SerializedName("propietario") val propietario: String,
                      @SerializedName("autores") val autores: List<Author>,
                      @SerializedName("lecturas") val lecturas: List<Reading>) {

    fun getRatingOrDefault(): Int {
        return if (lecturas.isNotEmpty()) lecturas[0].valoracion else 0
    }

    fun formattedPurchaseDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return if (fechaCompra != null) dateFormat.format(fechaCompra) else ""
    }

    fun authorsNames(): String {
        return autores.joinToString(", ") { it.name }
    }
}