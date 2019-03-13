package br.com.tatianefx.movies.network

import br.com.tatianefx.movies.data.Movie
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

class ApiClient {

    companion object {

        private val service: ApiService by lazy {
            NetworkClient.getApi()
        }

        fun getMovieByTitle(title: String, listener: OnResponseListener<Movie>) {
            service.getMovieByTitle(title).enqueue(object : Callback<Movie> {

                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    response.body()?.let {
                        listener.onSuccess(it)
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    listener.onFailure(t.message ?: "Request Failure")
                }
            })
        }
    }
}