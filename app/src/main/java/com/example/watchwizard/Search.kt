package com.example.watchwizard
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watchwizard.R
import retrofit2.Call
import retrofit2.Response
import java.util.*
import android.Manifest

class Search : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: MovieAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var micButton: ImageButton
    private lateinit var speechRecognizer: SpeechRecognizer



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.search_recycler_view)
        searchView = view.findViewById(R.id.search_bar)
        micButton = view.findViewById(R.id.mic_button)

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

        micButton.setOnClickListener {
            if (isSpeechRecognizerPermissionGranted()) {
                startSpeechRecognition()
            } else {
                requestSpeechRecognizerPermission()
            }
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                Log.e("SpeechRecognition", "Error: $error")
                Toast.makeText(context, "Speech recognition error", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val query = matches[0]
                    searchView.setQuery(query, true)
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}
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

    private fun isSpeechRecognizerPermissionGranted(): Boolean {
        val permission = Manifest.permission.RECORD_AUDIO
        return ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestSpeechRecognizerPermission() {
        val permission = Manifest.permission.RECORD_AUDIO
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), RECORD_AUDIO_PERMISSION_REQUEST_CODE)
    }

    private fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to search...")
        speechRecognizer.startListening(intent)
    }

    companion object {
        private const val RECORD_AUDIO_PERMISSION_REQUEST_CODE = 123
    }
}



