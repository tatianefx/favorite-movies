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

        fun getMovieByTitle(title: String, listener: OnResponseListener<List<Movie>>) {
            service.getMovieByTitle(title).enqueue(object : Callback<List<Movie>> {

                override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                    response.body()?.let {
                        listener.onSuccess(it)
                    }
                }

                override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                    listener.onFailure(t.message ?: "Request Failure")
                }
            })
        }
    }
}