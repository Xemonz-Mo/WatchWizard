package com.example.watchwizard

import Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class MovieDetail : AppCompatActivity() {

    private lateinit var poster: ImageView
    private lateinit var backdrop: ImageView
    private lateinit var title: TextView
    private lateinit var release_date: TextView
    private lateinit var overview: TextView
    private lateinit var votes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        poster = findViewById(R.id.poster)
        title = findViewById(R.id.title)
        release_date = findViewById(R.id.release_date)
        overview = findViewById(R.id.overview)
        votes = findViewById(R.id.vote_count)
        backdrop = findViewById(R.id.backdrop)

        val movie = intent.getParcelableExtra<Movie>("movie")

        if (movie != null) {
            val vote = movie.voteAverage.toString()
            title.text = movie.title
            release_date.text = "Released: ${movie.releaseDate}"
            overview.text = movie.overview
            votes.text = "Rating: $vote"
            Picasso.get().load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(poster)
            Picasso.get().load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
                .into(backdrop)
        }
    }
}


