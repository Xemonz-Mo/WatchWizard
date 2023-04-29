package com.example.watchwizard
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watchwizard.R
import retrofit2.Call
import retrofit2.Response

class Search : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: MovieAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.search_recycler_view)
        searchView = view.findViewById(R.id.search_bar)

        layoutManager = GridLayoutManager(this.context, 2)
        recyclerView.layoutManager = layoutManager

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchMovies(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return view
    }

    private fun searchMovies(query: String) {
        val call = RetrofitClient.retrofit.searchMovies(RetrofitClient.API_KEY, query)
        call.enqueue(object : retrofit2.Callback<MovieListResponse> {
            override fun onResponse(call: Call<MovieListResponse>, response: Response<MovieListResponse>) {
                if (response.isSuccessful) {
                    val movieListResponse = response.body()
                    val movies = movieListResponse?.movies ?: emptyList()
                    adapter = MovieAdapter(movies.toMutableList())
                    recyclerView.adapter = adapter
                } else {
                    Log.e("Retrofit", "Error searching for movies")
                    Toast.makeText(context, "Failed to search for movies", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                Log.e("Retrofit", "Error searching for movies", t)
                Toast.makeText(context, "Failed to search for movies", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

