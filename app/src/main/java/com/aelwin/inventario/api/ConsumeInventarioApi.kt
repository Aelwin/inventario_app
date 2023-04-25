package com.aelwin.inventario.api

import com.aelwin.inventario.util.Constantes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConsumeInventarioApi {

    companion object {

        private val retrofit = getRetrofit().create(InventarioApiService::class.java)

        suspend fun getAuthors(): List<Author> {
            val myResponse = retrofit.getAuthors()
            return myResponse.body().orEmpty()
        }

        suspend fun getAuthors(name: String): List<Author> {
            val myResponse = retrofit.getAuthors(name)
            return myResponse.body().orEmpty()
        }

        suspend fun getAuthor(id: Int): Author? {
            val myResponse = retrofit.getAuthor(id)
            return myResponse.body()
        }

        suspend fun createAuthor(author: AuthorCreate): AuthorCreateResponse? {
            val myResponse = retrofit.createAuthor(author)
            return myResponse.body()
        }

        suspend fun getBook(id: Int): BookDetail? {
            val myResponse = retrofit.getBook(id)
            return myResponse.body()
        }

        suspend fun getReadings(bookID: Int): List<Reading> {
            val myResponse = retrofit.getReadings(bookID)
            return myResponse.body().orEmpty()
        }

        suspend fun createReading(reading: ReadingCreate): ReadingCreateResponse? {
            val myResponse = retrofit.createReading(reading)
            return myResponse.body()
        }

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constantes.SPACE_APP)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}