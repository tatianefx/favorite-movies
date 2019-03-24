package br.com.tatianefx.movies.data.source

import android.content.Context
import br.com.tatianefx.movies.data.Movie


/**
 * Created by Tatiane Souza on 12/03/2019.
 */

interface MoviesDataSource {

    interface LoadMoviesCallback {

        fun onMoviesLoaded(movies: List<Movie>)

        fun onDataNotAvailable()

        fun onFailure(message: String)
    }

    interface GetMovieCallback {

        fun onMovieLoaded(movie: Movie)

        fun onDataNotAvailable()

        fun onFailure(message: String)
    }

    fun getMovies(callback: LoadMoviesCallback)

    fun searchMovies(context: Context, title: String, page: Int, callback: LoadMoviesCallback)

    fun getMovie(context: Context, imdbId: String, callback: GetMovieCallback)

    fun saveMovie(movie: Movie)

    fun deleteAllMovies()

    fun deleteMovie(imdbId: String)
}