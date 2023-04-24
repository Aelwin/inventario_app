package com.aelwin.inventario.api

import com.aelwin.inventario.util.Utilidades
import com.google.gson.annotations.SerializedName

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
                      @SerializedName("fecha_compra") val fechaCompra: String,
                      @SerializedName("categoria") val categoria: String,
                      @SerializedName("saga") val saga: String,
                      @SerializedName("propietario") val propietario: String,
                      @SerializedName("autores") val autores: List<Author>,
                      @SerializedName("lecturas") val lecturas: List<RatingReading>) {

    fun getRatingOrDefault(): Int {
        return if (lecturas.isNotEmpty()) lecturas[0].valoracion else 0
    }

    fun formattedPurchaseDate(): String {
        return Utilidades.formatDateFromApi(fechaCompra)
    }

    fun authorsNames(): String {
        return autores.joinToString(", ") { it.name }
    }
}