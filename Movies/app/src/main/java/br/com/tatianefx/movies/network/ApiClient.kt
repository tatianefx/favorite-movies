package br.com.tatianefx.movies.network

import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.data.MovieDetail
import br.com.tatianefx.movies.data.Search
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

class ApiClient {

    companion object {

        private val service: ApiService by lazy {
            NetworkClient.getApi()
        }

        fun searchMovieByTitle(title: String, listener: OnResponseListener<List<Movie>>) {
            service.searchMovieByTitle(title).enqueue(object : Callback<Search> {

                override fun onResponse(call: Call<Search>, response: Response<Search>) {
                    response.body()?.let {
                        listener.onSuccess(it.movies ?: arrayListOf())
                    }
                }

                override fun onFailure(call: Call<Search>, t: Throwable) {
                    listener.onFailure(t.message ?: "Request Failure")
                }
            })
        }

        fun getMovieDetails(imdbId: String, listener: OnResponseListener<MovieDetail>) {
            service.getMovieDetails(imdbId).enqueue(object : Callback<MovieDetail> {

                override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                    response.body()?.let {
                        listener.onSuccess(it)
                    }
                }

                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                    listener.onFailure(t.message ?: "Request Failure")
                }
            })
        }
    }
}