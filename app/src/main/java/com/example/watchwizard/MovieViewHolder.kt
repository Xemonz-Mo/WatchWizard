package com.example.watchwizard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var posterImage : ImageView
    var movieTitle : TextView

    init {
        posterImage = itemView.findViewById(R.id.moviePoster)
        movieTitle = itemView.findViewById(R.id.movieTitle)
    }
}