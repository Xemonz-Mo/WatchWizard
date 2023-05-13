package com.example.watchwizard

import Movie
import com.google.gson.annotations.SerializedName


class MovieListResponse {
    @SerializedName("results")
    val movies: List<Movie>? = null
}
