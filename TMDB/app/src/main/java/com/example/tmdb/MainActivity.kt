package com.example.tmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val API_KEY: String = "api_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val request = ServiceBuilder.buildService(tmdbEndPoint::class.java)
        val call = request.getMovies(API_KEY)

        call.enqueue(object: Callback<PopularMovies>{
            override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PopularMovies>, response: Response<PopularMovies>) {
                if(response.isSuccessful){
                    progressBar2.visibility = View.GONE
                    recyclerView_movies.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = MoviesRecyclerAdapter(response.body()!!.results)
                    }
                }
            }
        })
    }
}