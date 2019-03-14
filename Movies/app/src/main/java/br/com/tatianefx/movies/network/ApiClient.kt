package br.com.tatianefx.movies.network

import br.com.tatianefx.movies.data.Movie
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

        fun getMovieByTitle(title: String, page: Int?, listener: OnResponseListener<List<Movie>>) {
            service.getMovieByTitle(title, page).enqueue(object : Callback<Search> {

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
    }
}