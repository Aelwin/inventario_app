package com.aelwin.inventario.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Utilidades {

    companion object {
        fun formatDateFromApi(fecha: String?): String {
            // Primero pasamos el string con formato del API a LocalDate y luego lo formateamos como queremos que se muestre
            return if (fecha != null)
                LocalDate.parse(fecha, DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA_API))
                .format(DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA))
            else ""
        }
    }
}