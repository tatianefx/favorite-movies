package br.com.tatianefx.movies.data.source.remote

import android.content.Context
import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.data.source.MoviesDataSource
import br.com.tatianefx.movies.network.ApiClient
import br.com.tatianefx.movies.network.OnResponseListener
import br.com.tatianefx.movies.util.NetworkUtil
import okhttp3.ResponseBody


/**
 * Created by Tatiane Souza on 14/03/2019.
 */

class MoviesRemoteDataSource: MoviesDataSource {

    override fun getMovies(callback: MoviesDataSource.LoadMoviesCallback) {
        // do nothing
    }

    override fun searchMovies(context: Context, title: String, callback: MoviesDataSource.LoadMoviesCallback) {
        // verifies if internet is available
        if (!NetworkUtil.isNetworkAvailable(context)) {
            callback.onFaliure("No internet")
            return
        }

        ApiClient.searchMovieByTitle(title, object: OnResponseListener<List<Movie>> {
            override fun onSuccess(response: List<Movie>) {
                callback.onMoviesLoaded(response)
            }

            override fun onError(body: ResponseBody, code: Int) {
                callback.onFaliure("Error")
            }

            override fun onFailure(str: String) {
                callback.onFaliure(str)
            }
        })
    }

    override fun getMovie(context: Context, imdbId: String, callback: MoviesDataSource.GetMovieCallback) {
        // verifies if internet is available
        if (!NetworkUtil.isNetworkAvailable(context)) {
            callback.onFaliure("No internet")
            return
        }

        ApiClient.getMovieDetails(imdbId, object: OnResponseListener<Movie> {
            override fun onSuccess(response: Movie) {
                callback.onMovieLoaded(response)
            }

            override fun onError(body: ResponseBody, code: Int) {
                callback.onFaliure("Error")
            }

            override fun onFailure(str: String) {
                callback.onFaliure(str)
            }
        })
    }

    override fun saveMovie(movie: Movie) {
        // do nothing
    }

    override fun deleteAllMovies() {
        // do nothing
    }

    override fun deleteMovie(id: String) {
        // do nothing
    }

}