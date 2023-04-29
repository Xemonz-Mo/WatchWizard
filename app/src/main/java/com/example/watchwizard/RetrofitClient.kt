package com.example.watchwizard

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "db56e2ce483e8b566cf6a3e590f99b7d"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)


    }
}