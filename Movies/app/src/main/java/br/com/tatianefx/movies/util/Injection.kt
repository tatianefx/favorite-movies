package br.com.tatianefx.movies.util

import android.content.Context
import br.com.tatianefx.movies.data.source.MoviesDataSource
import br.com.tatianefx.movies.data.source.MoviesRepository
import br.com.tatianefx.movies.data.source.local.MoviesDatabase
import br.com.tatianefx.movies.data.source.local.MoviesLocalDataSource

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

/**
 * Enables injection of production implementations for
 * [MoviesDataSource] at compile time.
 */
object Injection {

    fun provideMoviesRepository(context: Context): MoviesRepository {
        val database = MoviesDatabase.getInstance(context)
        return MoviesRepository.getInstance(MoviesLocalDataSource.getInstance(AppExecutors(), database.moviesDao()))
    }
}
