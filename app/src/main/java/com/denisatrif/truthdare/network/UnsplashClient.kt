package com.denisatrif.truthdare.network

import com.denisatrif.truthdare.network.model.UnsplashImage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private const val BASE_URL = "https://api.unsplash.com"

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


interface UnsplashService {

    @Headers("Authorization: Client-ID zLUcmuu4oNHaAGf4n9JbPYxqcVo4qZqssiMTP40ZisA")
    @GET("/photos/random")
    suspend fun getRandomPhotoWithTopic(): UnsplashImage
}

object UnsplashApi {
    val retrofitService: UnsplashService by lazy { retrofit.create(UnsplashService::class.java) }
}
