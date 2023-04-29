package com.example.watchwizard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response


class Home: Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: MovieAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.trending_movies_container)
        layoutManager = GridLayoutManager(this.context, 2)
        recyclerView.layoutManager = layoutManager
        loadTrendingMovies()
        return view
    }

    private fun loadTrendingMovies() {
        val call = RetrofitClient.retrofit.getTrendingMovies(RetrofitClient.API_KEY)
        call.enqueue(object : retrofit2.Callback<MovieListResponse> {
            override fun onResponse(call: Call<MovieListResponse>, response: Response<MovieListResponse>) {
                if (response.isSuccessful) {
                    val movieListResponse = response.body()
                    val movies = movieListResponse?.movies ?: emptyList()
                    adapter = MovieAdapter(movies.toMutableList())
                    recyclerView.adapter = adapter
                } else {
                    Log.e("Retrofit", "Error getting popular movies")
                    Toast.makeText(context, "Failed to fetch movies", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                Log.e("Retrofit", "Error getting popular movies", t)
                Toast.makeText(context, "Failed to fetch movies", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

