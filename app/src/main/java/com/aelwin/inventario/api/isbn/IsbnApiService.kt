package com.aelwin.inventario.api.isbn

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IsbnApiService {

    @GET("volumes")
    suspend fun getImageUrl(@Query("q") query: String): Response<IsbnResponse>
}