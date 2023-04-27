package com.aelwin.inventario.api

import com.aelwin.inventario.util.Utilidades
import com.google.gson.annotations.SerializedName

data class Book(@SerializedName("id") val id: Int,
                @SerializedName("titulo") val title: String)

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

data class BookCreate(@SerializedName("titulo") val titulo: String,
                      @SerializedName("propietario") val propietario: String,
                      @SerializedName("formato") val formato: String,
                      @SerializedName("idioma") val idioma: String,
                      @SerializedName("precio") val precio: Float,
                      @SerializedName("isbn") val isbn: String,
                      @SerializedName("sinopsis") val sinopsis: String,
                      @SerializedName("imagen") val imagen: String,
                      @SerializedName("editorial") val editorial: String,
                      @SerializedName("fecha_compra") val fechaCompra: String,
                      @SerializedName("observaciones") val observaciones: String,
                      @SerializedName("categoria") val categoria: String,
                      @SerializedName("saga") val saga: String,
                      @SerializedName("autores") val autores: List<AuthorBook>) {

    data class Builder(
        var titulo: String = "",
        var propietario: String = "",
        var formato: String = "",
        var idioma: String = "",
        var precio: Float = 0.0f,
        var isbn: String = "",
        var sinopsis: String = "",
        var imagen: String = "",
        var editorial: String = "",
        var fechaCompra: String = "",
        var observaciones: String = "",
        var categoria: String = "",
        var saga: String = "",
        var autores: List<AuthorBook> = listOf()
    ) {
        fun withTitulo(titulo: String) = apply { this.titulo = titulo }
        fun withPropietario(propietario: String) = apply { this.propietario = propietario }
        fun withFormato(formato: String) = apply { this.formato = formato }
        fun withIdioma(idioma: String) = apply { this.idioma = idioma }
        fun withPrecio(precio: Float) = apply { this.precio = precio }
        fun withIsbn(isbn: String) = apply { this.isbn = isbn }
        fun withSinopsis(sinopsis: String) = apply { this.sinopsis = sinopsis }
        fun withImagen(imagen: String) = apply { this.imagen = imagen }
        fun withEditorial(editorial: String) = apply { this.editorial = editorial }
        fun withFechaCompra(fechaCompra: String) = apply { this.fechaCompra = fechaCompra }
        fun withObservaciones(observaciones: String) = apply { this.observaciones = observaciones }
        fun withCategoria(categoria: String) = apply { this.categoria = categoria }
        fun withSaga(saga: String) = apply { this.saga = saga }
        fun withAutores(autores: List<AuthorBook>) = apply { this.autores = autores }

        fun build() = BookCreate(
            titulo = titulo,
            propietario = propietario,
            formato = formato,
            idioma = idioma,
            precio = precio,
            isbn = isbn,
            sinopsis = sinopsis,
            imagen = imagen,
            editorial = editorial,
            fechaCompra = fechaCompra,
            observaciones = observaciones,
            categoria = categoria,
            saga = saga,
            autores = autores
        )
    }
}