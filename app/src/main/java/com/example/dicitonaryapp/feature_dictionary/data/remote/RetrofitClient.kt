package com.example.dicitonaryapp.feature_dictionary.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.dictionaryapi.dev/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dictionaryApiService: DictionaryApi by lazy {
        retrofit.create(DictionaryApi::class.java)
    }
}