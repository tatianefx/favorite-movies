package br.com.tatianefx.movies.data.source.remote

import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.data.source.MoviesDataSource
import br.com.tatianefx.movies.network.ApiClient
import br.com.tatianefx.movies.network.OnResponseListener
import okhttp3.ResponseBody


/**
 * Created by Tatiane Souza on 14/03/2019.
 */

class MoviesRemoteDataSource: MoviesDataSource {

    override fun getMovies(callback: MoviesDataSource.LoadMoviesCallback) {
        // do nothing
    }

    override fun searchMovies(title: String, callback: MoviesDataSource.LoadMoviesCallback) {
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

            override fun noInternet() {
                callback.onFaliure("No internet")
            }
        })
    }

    override fun getMovie(imdbId: String, callback: MoviesDataSource.GetMovieCallback) {
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

            override fun noInternet() {
                callback.onFaliure("No internet")
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