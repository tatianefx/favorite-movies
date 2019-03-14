package br.com.tatianefx.movies.network

import br.com.tatianefx.movies.data.MovieDetail
import br.com.tatianefx.movies.data.Search
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

interface ApiService {

    @GET("/")
    fun searchMovieByTitle(@Query("s") title: String): Call<Search>

    @GET("/")
    fun getMovieDetails(@Query("i") imdbId: String): Call<MovieDetail>
}