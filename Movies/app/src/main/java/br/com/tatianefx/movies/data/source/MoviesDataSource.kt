package br.com.tatianefx.movies.data.source

import br.com.tatianefx.movies.data.Movie


/**
 * Created by Tatiane Souza on 12/03/2019.
 */

interface MoviesDataSource {

    interface LoadMoviesCallback {

        fun onMoviesLoaded(movies: List<Movie>)

        fun onDataNotAvailable()
    }

    interface GetMovieCallback {

        fun onMovieLoaded(movie: Movie)

        fun onDataNotAvailable()
    }

    fun getMovies(callback: LoadMoviesCallback)

    fun getMovie(id: String, callback: GetMovieCallback)

    fun saveMovie(movie: Movie)

    fun deleteAllMovies()

    fun deleteMovie(id: String)
}