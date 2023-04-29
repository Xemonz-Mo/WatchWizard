package com.example.watchwizard

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApiService {
    @GET("movie/popular")
    fun getTrendingMovies(
        @Query("api_key") API_KEY: String
    ): Call<MovieListResponse>

    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<MovieListResponse>

}