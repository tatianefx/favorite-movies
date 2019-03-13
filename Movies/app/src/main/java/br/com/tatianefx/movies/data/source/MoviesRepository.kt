package br.com.tatianefx.movies.data.source

import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.data.source.local.MoviesLocalDataSource


/**
 * Created by Tatiane Souza on 12/03/2019.
 */
class MoviesRepository(
    private val moviesLocalDataSource: MoviesDataSource
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
            callback.onMoviesLoaded(ArrayList(cachedMovies.values))
            return
        }

//        EspressoIdlingResource.increment() // Set app as busy.


        // Query the local storage if available. If not, query the network.
        moviesLocalDataSource.getMovies(object : MoviesDataSource.LoadMoviesCallback {
            override fun onMoviesLoaded(movies: List<Movie>) {
                refreshCache(movies)
//                    EspressoIdlingResource.decrement() // Set app as idle.
                callback.onMoviesLoaded(ArrayList(cachedMovies.values))
            }

            override fun onDataNotAvailable() {
//                    getMoviesFromRemoteDataSource(callback)
            }
        })
    }

    override fun saveMovie(movie: Movie) {
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
    override fun getMovie(id: String, callback: MoviesDataSource.GetMovieCallback) {
        val movieInCache = getMovieWithId(id)

        // Respond immediately with cache if available
        if (movieInCache != null) {
            callback.onMovieLoaded(movieInCache)
            return
        }

//        EspressoIdlingResource.increment() // Set app as busy.

        // Load from server/persisted if needed.

        // Is the movie in the local data source? If not, query the network.
        moviesLocalDataSource.getMovie(id, object : MoviesDataSource.GetMovieCallback {
            override fun onMovieLoaded(movie: Movie) {
                // Do in memory cache update to keep the app UI up to date
                cacheAndPerform(movie) {
//                    EspressoIdlingResource.decrement() // Set app as idle.
                    callback.onMovieLoaded(it)
                }
            }

            override fun onDataNotAvailable() {
                //TODO
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

    // Cache

    private fun refreshCache(movies: List<Movie>) {
        cachedMovies.clear()
        movies.forEach {
            cacheAndPerform(it) {}
        }
        cacheIsDirty = false
    }

    private fun getMovieWithId(id: String) = cachedMovies[id]

    private inline fun cacheAndPerform(movie: Movie, perform: (Movie) -> Unit) {
        val cachedMovie = Movie(movie.title, movie.year, movie.plot, movie.poster, movie.id)
        cachedMovies[cachedMovie.id] = cachedMovie
        perform(cachedMovie)
    }

    companion object {

        private var INSTANCE: MoviesRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param moviesLocalDataSource  the device storage data source
         * *
         * @return the [MoviesRepository] instance
         */
        @JvmStatic fun getInstance(moviesLocalDataSource: MoviesDataSource) =
            INSTANCE ?: synchronized(MoviesRepository::class.java) {
                INSTANCE ?: MoviesRepository(moviesLocalDataSource)
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
