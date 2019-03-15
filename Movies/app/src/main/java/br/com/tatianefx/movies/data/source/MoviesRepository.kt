package br.com.tatianefx.movies.data.source

import android.content.Context
import br.com.tatianefx.movies.data.Movie


/**
 * Created by Tatiane Souza on 12/03/2019.
 */
class MoviesRepository(
    private val moviesLocalDataSource: MoviesDataSource,
    private val moviesRemoteDataSource: MoviesDataSource
) : MoviesDataSource {

    /**
     * This variable has public visibility so it can be accessed from tests.
     */
    var cachedMovies: LinkedHashMap<String, Movie> = LinkedHashMap()

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    var cacheIsDirty = false

    /**
     * Gets movies from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     *
     *
     * Note: [LoadMoviesCallback.onDataNotAvailable] is fired if all data sources fail to
     * get the data.
     */
    override fun getMovies(callback: MoviesDataSource.LoadMoviesCallback) {
        // Respond immediately with cache if available and not dirty
        if (cachedMovies.isNotEmpty() && !cacheIsDirty) {
            callback.onMoviesLoaded(ArrayList(cachedMovies.values.filter { it.isFavorite }))
            return
        }

        // Query the local storage if available. If not, query the network.
        moviesLocalDataSource.getMovies(object : MoviesDataSource.LoadMoviesCallback {
            override fun onMoviesLoaded(movies: List<Movie>) {
                refreshCache(movies)
                callback.onMoviesLoaded(ArrayList(cachedMovies.values))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

            override fun onFaliure(message: String) {
                callback.onFaliure(message)
            }
        })
    }

    override fun saveMovie(movie: Movie) {
        movie.isFavorite = true
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(movie) {
            moviesLocalDataSource.saveMovie(it)
        }
    }

    /**
     * Gets movies from local data source (sqlite) unless the table is new or empty. In that case it
     * uses the network data source. This is done to simplify the sample.
     *
     *
     * Note: [GetMovieCallback.onDataNotAvailable] is fired if both data sources fail to
     * get the data.
     */
    override fun getMovie(context: Context, imdbId: String, callback: MoviesDataSource.GetMovieCallback) {
        val movieInCache = getMovieWithImdb(imdbId)

        // Respond immediately with cache if available
        if (movieInCache != null) {
            callback.onMovieLoaded(movieInCache)
            return
        }

        // Load from server/persisted if needed.

        // Is the movie in the local data source? If not, query the network.
        moviesLocalDataSource.getMovie(context, imdbId, object : MoviesDataSource.GetMovieCallback {
            override fun onMovieLoaded(movie: Movie) {
                // Do in memory cache update to keep the app UI up to date
                cacheAndPerform(movie) {
                    callback.onMovieLoaded(it)
                }
            }

            override fun onDataNotAvailable() {
                requestByApi(context, imdbId, callback)
            }

            override fun onFaliure(message: String) {
                callback.onFaliure(message)
            }
        })
    }

    override fun deleteAllMovies() {
        moviesLocalDataSource.deleteAllMovies()
        cachedMovies.clear()
    }

    override fun deleteMovie(id: String) {
        moviesLocalDataSource.deleteMovie(id)
        cachedMovies.remove(id)
    }

    override fun searchMovies(context: Context, title: String, callback: MoviesDataSource.LoadMoviesCallback) {
        moviesRemoteDataSource.searchMovies(context, title, object : MoviesDataSource.LoadMoviesCallback {
            override fun onMoviesLoaded(movies: List<Movie>) {
                callback.onMoviesLoaded(movies)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

            override fun onFaliure(message: String) {
                callback.onFaliure(message)
            }
        })
    }

    //region Remote

    private fun requestByApi(context: Context, imdbId: String, callback: MoviesDataSource.GetMovieCallback) {
        moviesRemoteDataSource.getMovie(context, imdbId, object : MoviesDataSource.GetMovieCallback {
            override fun onMovieLoaded(movie: Movie) {
                // Do in memory cache update to keep the app UI up to date
                cacheAndPerform(movie) {
                    callback.onMovieLoaded(it)
                }
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

            override fun onFaliure(message: String) {
                callback.onFaliure(message)
            }
        })
    }

    //endregion

    //region Cache

    private fun refreshCache(movies: List<Movie>) {
        cachedMovies.clear()
        movies.forEach {
            cacheAndPerform(it) {}
        }
        cacheIsDirty = false
    }

    private fun getMovieWithImdb(imdbId: String) = cachedMovies[imdbId]

    private inline fun cacheAndPerform(movie: Movie, perform: (Movie) -> Unit) {
        val cachedMovie = Movie(
            movie.isFavorite,
            movie.title,
            movie.year,
            movie.plot,
            movie.poster,
            movie.runtime,
            movie.genre,
            movie.director,
            movie.writer,
            movie.actors,
            movie.imdbId
        )
        cachedMovies[cachedMovie.imdbId] = cachedMovie
        perform(cachedMovie)
    }

    //endregion

    companion object {

        private var INSTANCE: MoviesRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param moviesLocalDataSource  the device storage data source
         * *
         * @param moviesRemoteDataSource the backend data source
         * *
         * @return the [MoviesRepository] instance
         */
        @JvmStatic fun getInstance(moviesLocalDataSource: MoviesDataSource, moviesRemoteDataSource: MoviesDataSource) =
            INSTANCE ?: synchronized(MoviesRepository::class.java) {
                INSTANCE ?: MoviesRepository(moviesLocalDataSource, moviesRemoteDataSource)
                    .also { INSTANCE = it }
            }


        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}
