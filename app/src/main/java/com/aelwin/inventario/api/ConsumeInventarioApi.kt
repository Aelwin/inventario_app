package com.aelwin.inventario.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

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
                .baseUrl("https://24an09.deta.dev/")
                .client(getCustomHttpClient()) //Tengo que crearme un cliente custom para que no haga comprobaciones SSL
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun getCustomHttpClient(): OkHttpClient {
            // Creamos un TrustManager personalizado que acepta todos los certificados
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                    // No hacemos nada
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                    // No hacemos nada
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })

            // Creamos un SSLContext y lo configuramos para usar nuestro TrustManager personalizado
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Configuramos OkHttpClient para que use nuestro SSLContext personalizado
            return OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }
                .build()
        }
    }
}