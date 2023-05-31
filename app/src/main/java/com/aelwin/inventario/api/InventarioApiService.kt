package com.aelwin.inventario.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InventarioApiService {

    //El suspend hace que no se ejecute en el hilo principal(asincrono)
    //@GET("libros/titulo/{title}")
    //suspend fun getBooks(@Path("title") title: String): Response<List<BookListDataResponse>>

    @GET("libros/{id}")
    suspend fun getBook(@Path("id") id: Int): Response<BookDetail>

    @GET("libros/{id}/lecturas")
    suspend fun getReadings(@Path("id") id: Int): Response<List<Reading>>

    @GET("autores")
    suspend fun getAuthors(): Response<List<Author>>

    @GET("autores/nombre/{name}")
    suspend fun getAuthors(@Path("name") name: String): Response<List<Author>>

    @GET("autores/{id}")
    suspend fun getAuthor(@Path("id") id: Int): Response<Author>

    @POST("autores/")
    suspend fun createAuthor(@Body author: AuthorCreate): Response<AuthorCreateResponse>

    @POST("lecturas/")
    suspend fun createReading(@Body reading: ReadingCreate): Response<ReadingCreateResponse>

    @DELETE("lecturas/{id}")
    suspend fun deleteReading(@Path("id") id: Int): Response<Void>

    @POST("libros/")
    suspend fun createBook(@Body book: BookCreate): Response<Book>
}