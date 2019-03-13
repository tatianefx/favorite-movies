package br.com.tatianefx.movies.network

import br.com.tatianefx.movies.data.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

interface ApiService {

    @GET("/")
    fun getMovieByTitle(@Query("s") title: String): Call<List<Movie>>
}