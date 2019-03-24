package br.com.tatianefx.movies.data.source.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.data.source.MoviesDataSource
import br.com.tatianefx.movies.util.SingleExecutors
import org.hamcrest.core.Is
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

/**
 * Created by tatiane.silva on 18/03/2019.
 */

@RunWith(AndroidJUnit4::class) @LargeTest class MoviesLocalDataSourceTest {

    private lateinit var localDataSource: MoviesLocalDataSource
    private lateinit var database: MoviesDatabase

    @Before
    fun setup() {
        // using an in-memory database for testing, since it doesn't survive killing the process
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            MoviesDatabase::class.java)
            .build()

        // Make sure that we're not keeping a reference to the wrong instance.
        MoviesLocalDataSource.clearInstance()
        localDataSource = MoviesLocalDataSource.getInstance(SingleExecutors(), database.moviesDao())
    }

    @After
    fun cleanUp() {
        database.close()
        MoviesLocalDataSource.clearInstance()
    }

    @Test
    fun testPreConditions() {
        Assert.assertNotNull(localDataSource)
    }


    @Test fun saveMovie_retrievesMovie() {
        // Given a new movie
        val newMovie = DEFAULT_MOVIE

        with(localDataSource) {
            // When saved into the persistent repository
            saveMovie(newMovie)

            // Then the movie can be retrieved from the persistent repository
            getMovie(InstrumentationRegistry.getInstrumentation().context, newMovie.imdbId, object : MoviesDataSource.GetMovieCallback {

                override fun onMovieLoaded(movie: Movie) {
                    Assert.assertThat(movie, Is.`is`(newMovie))
                }

                override fun onDataNotAvailable() {
                    Assert.fail("Callback error")
                }

                override fun onFailure(message: String) {
                    Assert.fail("Callback failure")
                }
            })
        }
    }

    @Test fun getMovies_retrieveSavedMovies() {
        // Given 2 new movies in the persistent repository
        val newMovie1 = Movie(
            DEFAULT_IS_FAVORITE,
            DEFAULT_TITLE,
            DEFAULT_YEAR,
            DEFAULT_PLOT,
            DEFAULT_POSTER,
            DEFAULT_RUNTIME,
            DEFAULT_GENRE,
            DEFAULT_DIRECTOR,
            DEFAULT_WRITER,
            DEFAULT_ACTORS,
            DEFAULT_IMDB_ID
        )
        val newMovie2 = Movie(
            DEFAULT_IS_FAVORITE,
            DEFAULT_TITLE,
            DEFAULT_YEAR,
            DEFAULT_PLOT,
            DEFAULT_POSTER,
            DEFAULT_RUNTIME,
            DEFAULT_GENRE,
            DEFAULT_DIRECTOR,
            DEFAULT_WRITER,
            DEFAULT_ACTORS,
            DEFAULT_IMDB_ID2
        )

        with(localDataSource) {
            saveMovie(newMovie1)
            saveMovie(newMovie2)
            // Then the tasks can be retrieved from the persistent repository
            getMovies(object : MoviesDataSource.LoadMoviesCallback {
                override fun onMoviesLoaded(movies: List<Movie>) {
                    Assert.assertNotNull(movies)
                    Assert.assertTrue(movies.size >= 2)

                    val result = movies.filter { it.imdbId == newMovie1.imdbId || it.imdbId == newMovie2.imdbId }

                    Assert.assertTrue(result.size == 2)
                }

                override fun onDataNotAvailable() {
                    Assert.fail()
                }

                override fun onFailure(message: String) {
                    Assert.fail()
                }
            })
        }
    }

    @Test fun deleteMovieByImdbId_emptyListOfRetrievedMovie() {
        val callback = Mockito.mock(MoviesDataSource.LoadMoviesCallback::class.java)

        // Given a new movie in the persistent repository and a mocked callback
        val newMovie = DEFAULT_MOVIE

        with(localDataSource) {
            saveMovie(newMovie)

            // When movie are deleted
            deleteMovie(DEFAULT_IMDB_ID)

            // Then the retrieved movies is an empty list
            getMovies(callback)
        }
        Mockito.verify<MoviesDataSource.LoadMoviesCallback>(callback).onDataNotAvailable()
        Mockito.verify<MoviesDataSource.LoadMoviesCallback>(callback, Mockito.never())
            .onMoviesLoaded(Mockito.any<List<Movie>>() ?: arrayListOf())
    }

    @Test fun deleteAllMovies_emptyListOfRetrievedMovie() {
        val callback = Mockito.mock(MoviesDataSource.LoadMoviesCallback::class.java)

        // Given a new movie in the persistent repository and a mocked callback
        val newMovie = DEFAULT_MOVIE

        with(localDataSource) {
            saveMovie(newMovie)

            // When all movies are deleted
            deleteAllMovies()

            // Then the retrieved movies is an empty list
            getMovies(callback)
        }
        Mockito.verify<MoviesDataSource.LoadMoviesCallback>(callback).onDataNotAvailable()
        Mockito.verify<MoviesDataSource.LoadMoviesCallback>(callback, Mockito.never())
            .onMoviesLoaded(Mockito.any<List<Movie>>()?: arrayListOf())
    }

    companion object {

        private const val DEFAULT_IS_FAVORITE = false
        private const val DEFAULT_TITLE = "title"
        private const val DEFAULT_YEAR = "year"
        private const val DEFAULT_PLOT = "plot"
        private const val DEFAULT_POSTER = "poster"
        private const val DEFAULT_RUNTIME = "runtime"
        private const val DEFAULT_GENRE = "genre"
        private const val DEFAULT_DIRECTOR = "director"
        private const val DEFAULT_WRITER = "writer"
        private const val DEFAULT_ACTORS = "actors"
        private const val DEFAULT_IMDB_ID = "imdbID"
        private const val DEFAULT_IMDB_ID2 = "imdbID2"

        private val DEFAULT_MOVIE = Movie(
            DEFAULT_IS_FAVORITE,
            DEFAULT_TITLE,
            DEFAULT_YEAR,
            DEFAULT_PLOT,
            DEFAULT_POSTER,
            DEFAULT_RUNTIME,
            DEFAULT_GENRE,
            DEFAULT_DIRECTOR,
            DEFAULT_WRITER,
            DEFAULT_ACTORS,
            DEFAULT_IMDB_ID
        )
    }
}