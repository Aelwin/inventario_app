package com.aelwin.inventario.api

import com.google.gson.annotations.SerializedName

data class Author(@SerializedName("id") val id: Int,
                  @SerializedName("nombre") val name: String,
                  @SerializedName("libros") val books: List<Book>) {
}

data class AuthorCreate(@SerializedName("nombre") val nombre: String) { }

data class AuthorCreateResponse(@SerializedName("id") val id: Int,
                                @SerializedName("nombre") val name: String) { }