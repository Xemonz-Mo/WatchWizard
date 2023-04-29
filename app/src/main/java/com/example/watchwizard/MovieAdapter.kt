package com.example.watchwizard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MovieAdapter (private val movieList : MutableList<Movie>) : RecyclerView.Adapter<MovieViewHolder>()  {

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        context = parent.context
        return MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false))
    }

    override fun getItemCount(): Int = movieList.size



    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movie = movieList[position]

        holder.movieTitle.text = movie.title

        Picasso.get().load("https://image.tmdb.org/t/p/w500${movie.posterPath}").into(holder.posterImage)
    }
}