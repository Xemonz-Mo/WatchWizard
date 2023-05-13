package com.example.watchwizard

import Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie/{id}")
    fun getMovieWithID(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Call<Movie>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendedMovies(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ): Call<MovieListResponse>
}