package com.example.watchwizard

import Movie
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watchwizard.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Watched : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieList: MutableList<Movie>
    private lateinit var moviePreferences: MoviePreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_watched, container, false)

        recyclerView = view.findViewById(R.id.watched_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        moviePreferences = MoviePreferences(requireContext())
        val watchedMovies = moviePreferences.getWatchedMovies()

        movieList = mutableListOf()



        for (movieId in watchedMovies) {
            val call = RetrofitClient.retrofit.getMovieWithID(movieId.toInt(), RetrofitClient.API_KEY)
            call.enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful) {
                        val movie = response.body()
                        movie?.let {
                            movieList.add(it)
                            movieAdapter.notifyDataSetChanged()
                        }
                    } else {
                        Log.e("Retrofit", "Error getting movie with ID: $movieId")
                        Toast.makeText(context, "Failed to fetch movie with ID: $movieId", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.e("Retrofit", "Error getting movie with ID: $movieId", t)
                    Toast.makeText(context, "Failed to fetch movie with ID: $movieId", Toast.LENGTH_SHORT).show()
                }
            })


        }

        movieAdapter = MovieAdapter(movieList)
        recyclerView.adapter = movieAdapter

        return view
    }
}
