package com.aelwin.inventario.api

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Reading(@SerializedName("valoracion") val valoracion: Int,
                   @SerializedName("fecha_inicio") val fechaInicio: Date,
                   @SerializedName("fecha_fin") val fechaFin: Date) {
}