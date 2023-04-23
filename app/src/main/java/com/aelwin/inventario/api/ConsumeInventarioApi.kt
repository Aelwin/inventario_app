package com.aelwin.inventario.api

import com.aelwin.inventario.util.Constantes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ConsumeInventarioApi {

    companion object {

        private val retrofit = getRetrofit()

        suspend fun getAuthors(): List<Author> {
            val myResponse = retrofit.create(InventarioApiService::class.java).getAuthors()
            return myResponse.body().orEmpty()
        }

        suspend fun getAuthors(name: String): List<Author> {
            val myResponse = retrofit.create(InventarioApiService::class.java).getAuthors(name)
            return myResponse.body().orEmpty()
        }

        suspend fun getAuthor(id: Int): Author? {
            val myResponse = retrofit.create(InventarioApiService::class.java).getAuthor(id)
            return myResponse.body()
        }

        suspend fun createAuthor(author: AuthorCreate): AuthorCreateResponse? {
            val myResponse = retrofit.create(InventarioApiService::class.java).createAuthor(author)
            return myResponse.body()
        }

        suspend fun getBook(id: Int): BookDetail? {
            val myResponse = retrofit.create(InventarioApiService::class.java).getBook(id)
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