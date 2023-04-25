package com.aelwin.inventario.api.isbn

import android.util.Log
import com.aelwin.inventario.util.Constantes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConsumeIsbnApi {

    companion object {

        private val retrofit = getRetrofit()

        suspend fun getImageUrl(isbn: String): String {
            Log.i("Annatar", "isbn:$isbn")
            val myResponse = retrofit.create(IsbnApiService::class.java).getImageUrl("isbn:$isbn")
            return when {
                myResponse.body()?.totalItems == 1 && myResponse.body()?.items?.get(0)?.volumeInfo?.imageLinks != null -> {
                    myResponse.body()!!.items[0].volumeInfo.imageLinks.urlImage.replace("http", "https")
                }
                else -> {
                    "cualquier cosa que no sea una url valida"
                }
            }
        }

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constantes.ISBN_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}