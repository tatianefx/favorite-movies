package br.com.tatianefx.movies.ui.common

import androidx.paging.PageKeyedDataSource
import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.network.ApiClient
import br.com.tatianefx.movies.network.OnResponseListener
import okhttp3.ResponseBody

/**
 * Created by tatiane.silva on 14/03/2019.
 */

class MoviesRemoteDataSource : PageKeyedDataSource<Int, Movie>() {

    private val FIRST_PAGE = 1

    private val movies: List<Movie> = emptyList()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        ApiClient.getMovieByTitle("", 1, object : OnResponseListener<List<Movie>>{
            override fun onSuccess(response: List<Movie>) {
                val initialData = movies.subList(0, params.requestedLoadSize)
                val pageBefore = FIRST_PAGE - 1
                val pageAfter = FIRST_PAGE + 1
                callback.onResult(initialData, pageBefore, pageAfter)
            }

            override fun onError(body: ResponseBody, code: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailure(str: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun noInternet() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        ApiClient.getMovieByTitle("", params.key, object : OnResponseListener<List<Movie>>{
            override fun onSuccess(response: List<Movie>) {
                val start = params.key * params.requestedLoadSize
                val afterData = movies.subList(start, start + params.requestedLoadSize)
                callback.onResult(afterData, params.key + 1)
            }

            override fun onError(body: ResponseBody, code: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailure(str: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun noInternet() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // do nothing
    }

}