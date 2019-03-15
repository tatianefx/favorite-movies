package br.com.tatianefx.movies.data.source.local

import android.content.Context
import androidx.annotation.VisibleForTesting
import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.data.source.MoviesDataSource
import br.com.tatianefx.movies.util.AppExecutors


/**
 * Created by Tatiane Souza on 12/03/2019.
 */

/**
 * Concrete implementation of a data source as a db.
 */
class MoviesLocalDataSource private constructor(
    private val appExecutors: AppExecutors,
    private val moviesDao: MoviesDao
) : MoviesDataSource {

    /**
     * Note: [LoadMoviesCallback.onDataNotAvailable] is fired if the database doesn't exist
     * or the table is empty.
     */
    override fun getMovies(callback: MoviesDataSource.LoadMoviesCallback) {
        appExecutors.diskIO.execute {
            val movies = moviesDao.getMovies()
            appExecutors.mainThread.execute {
                if (movies.isEmpty()) {
                    // This will be called if the table is new or just empty.
                    callback.onDataNotAvailable()
                } else {
                    callback.onMoviesLoaded(movies)
                }
            }
        }
    }

    /**
     * Note: [GetMoviesCallback.onDataNotAvailable] is fired if the [Movie] isn't
     * found.
     */
    override fun getMovie(context: Context, imdbId: String, callback: MoviesDataSource.GetMovieCallback) {
        appExecutors.diskIO.execute {
            val movie = moviesDao.getMovieByImdbId(imdbId)
            appExecutors.mainThread.execute {
                if (movie != null) {
                    callback.onMovieLoaded(movie)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun saveMovie(movie: Movie) {
        appExecutors.diskIO.execute { moviesDao.insertMovie(movie) }
    }

    override fun deleteAllMovies() {
        appExecutors.diskIO.execute { moviesDao.deleteMovies() }
    }

    override fun deleteMovie(imdbId: String) {
        appExecutors.diskIO.execute { moviesDao.deleteMobieByImdbID(imdbId) }
    }

    override fun searchMovies(context: Context, title: String, callback: MoviesDataSource.LoadMoviesCallback) {
        // do nothing
    }

    companion object {
        private var INSTANCE: MoviesLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, moviesDao: MoviesDao): MoviesLocalDataSource {
            if (INSTANCE == null) {
                synchronized(MoviesLocalDataSource::javaClass) {
                    INSTANCE = MoviesLocalDataSource(appExecutors, moviesDao)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}
