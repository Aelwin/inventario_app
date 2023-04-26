package com.aelwin.inventario.util

import android.widget.DatePicker
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

        fun getLocalDateFromDatePicker(datePicker: DatePicker): LocalDate? {
            return LocalDate.of(datePicker.year, datePicker.month, datePicker.dayOfMonth)
        }

        fun getStringApiDateFromDatePicker(datePicker: DatePicker): String? {
            return getStringDateFromDatePicker(datePicker, Constantes.FORMATO_FECHA_API)
        }

        fun getStringShowDateFromDatePicker(datePicker: DatePicker): String? {
            return getStringDateFromDatePicker(datePicker, Constantes.FORMATO_FECHA)
        }

        private fun getStringDateFromDatePicker(datePicker: DatePicker, format: String): String? {
            return getLocalDateFromDatePicker(datePicker)?.format(
                DateTimeFormatter.ofPattern(format)
            )
        }
    }
}