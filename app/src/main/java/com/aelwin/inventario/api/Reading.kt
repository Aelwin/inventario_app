package com.aelwin.inventario.api

import com.aelwin.inventario.util.Utilidades
import com.google.gson.annotations.SerializedName

data class RatingReading(@SerializedName("valoracion") val valoracion: Int) {
}

data class Reading(@SerializedName("id") val id: Int,
                    @SerializedName("valoracion") val valoracion: Int,
                    @SerializedName("fecha_inicio") val fechaInicio: String,
                    @SerializedName("fecha_fin") val fechaFin: String,
                    @SerializedName("lector") val lector: String,
                    @SerializedName("libro") val libro: Book) {

    fun fechaInicioFormateada(): String {
        return Utilidades.formatDateFromApi(fechaInicio)
    }

    fun fechaFinFormateada(): String {
        return Utilidades.formatDateFromApi(fechaFin)
    }

}