package com.example.watchwizard

import Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso

class MovieDetail : AppCompatActivity() {

    private lateinit var moviePreferences: MoviePreferences
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        moviePreferences = MoviePreferences(this)

        val poster: ImageView = findViewById(R.id.poster)
        val title: TextView = findViewById(R.id.title)
        val releaseDate: TextView = findViewById(R.id.release_date)
        val overview: TextView = findViewById(R.id.overview)
        val votes: TextView = findViewById(R.id.vote_count)
        val backdrop: ImageView = findViewById(R.id.backdrop)

        movie = intent.getParcelableExtra<Movie>("movie")!!

        val vote = movie.voteAverage.toString()
        title.text = movie.title
        releaseDate.text = "Released: ${movie.releaseDate}"
        overview.text = movie.overview
        votes.text = "Rating: $vote"

        Picasso.get().load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .into(poster)

        Picasso.get().load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
            .into(backdrop)

        val likeButton: ImageButton = findViewById(R.id.like_button)
        val unlikeButton: ImageButton = findViewById(R.id.unlike_button)

        if (moviePreferences.getLikedMovies().contains(movie.id.toString())) {
            likeButton.visibility = View.GONE
            unlikeButton.visibility = View.VISIBLE
        } else {
            likeButton.visibility = View.VISIBLE
            unlikeButton.visibility = View.GONE
        }

        likeButton.setOnClickListener {
            moviePreferences.addLikedMovie(movie.id.toString())
            likeButton.visibility = View.GONE
            unlikeButton.visibility = View.VISIBLE
            Toast.makeText(this, "${movie.title} Has been added to your likes!", Toast.LENGTH_SHORT).show()
        }

        unlikeButton.setOnClickListener {
            moviePreferences.removeLikedMovie(movie.id.toString())
            likeButton.visibility = View.VISIBLE
            unlikeButton.visibility = View.GONE
            Toast.makeText(this, "${movie.title} Has been removed to your likes!", Toast.LENGTH_SHORT).show()
        }


        val watchedButton: ImageButton = findViewById(R.id.watched_button)
        val unwatchButton: ImageButton = findViewById(R.id.unwatched_button)

        if (moviePreferences.getWatchedMovies().contains(movie.id.toString())) {
            watchedButton.visibility = View.GONE
            unwatchButton.visibility = View.VISIBLE
        } else {
            watchedButton.visibility = View.VISIBLE
            unwatchButton.visibility = View.GONE
        }

        watchedButton.setOnClickListener {
            moviePreferences.addWatchedMovie(movie.id.toString())
            watchedButton.visibility = View.GONE
            unwatchButton.visibility = View.VISIBLE
            Toast.makeText(this, "${movie.title} Has been added to your watched movies!", Toast.LENGTH_SHORT).show()
        }

        unwatchButton.setOnClickListener {
            moviePreferences.removeWatchedMovie(movie.id.toString())
            watchedButton.visibility = View.VISIBLE
            unwatchButton.visibility = View.GONE
            Toast.makeText(this, "${movie.title} Has been removed to your watched movies!", Toast.LENGTH_SHORT).show()
        }
    }
}
