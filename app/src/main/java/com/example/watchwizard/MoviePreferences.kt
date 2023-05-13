package com.example.watchwizard

import android.content.Context
import android.content.SharedPreferences

class MoviePreferences(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MoviePreferences", Context.MODE_PRIVATE)

    fun getLikedMovies(): Set<String> {
        return sharedPreferences.getStringSet("likedMovies", setOf()) ?: setOf()
    }

    fun addLikedMovie(movieId: String) {
        val likedMovies = getLikedMovies().toMutableSet()
        likedMovies.add(movieId)
        sharedPreferences.edit().putStringSet("likedMovies", likedMovies).apply()
    }

    fun removeLikedMovie(movieId: String) {
        val likedMovies = getLikedMovies().toMutableSet()
        likedMovies.remove(movieId)
        sharedPreferences.edit().putStringSet("likedMovies", likedMovies).apply()
    }


    fun getWatchedMovies(): Set<String> {
        return sharedPreferences.getStringSet("watchedMovies", setOf()) ?: setOf()
    }

    fun addWatchedMovie(movieId: String) {
        val watchedMovies = getWatchedMovies().toMutableSet()
        watchedMovies.add(movieId)
        sharedPreferences.edit().putStringSet("watchedMovies", watchedMovies).apply()
    }

    fun removeWatchedMovie(movieId: String) {
        val watchedMovies = getWatchedMovies().toMutableSet()
        watchedMovies.remove(movieId)
        sharedPreferences.edit().putStringSet("watchedMovies", watchedMovies).apply()
    }
}
