package br.com.tatianefx.movies.data.source.local

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import br.com.tatianefx.movies.data.Movie
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by tatiane.silva on 18/03/2019.
 */

@RunWith(AndroidJUnit4::class) @LargeTest class MoviesDaoTest {

    private lateinit var database: MoviesDatabase

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            MoviesDatabase::class.java).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertMovieAndGetByImdbId() {
        // When inserting a movie
        database.moviesDao().insertMovie(DEFAULT_MOVIE)

        // When getting the movie by id from the database
        val loaded = database.moviesDao().getMovieByImdbId(DEFAULT_MOVIE.imdbId)

        // The loaded data contains the expected values
        assertMovie(loaded, DEFAULT_IMDB_ID, DEFAULT_TITLE, DEFAULT_YEAR, DEFAULT_POSTER, DEFAULT_IS_FAVORITE)
    }

    @Test fun insertMovieReplacesOnConflict() {
        // Given that a movie is inserted
        database.moviesDao().insertMovie(DEFAULT_MOVIE)

        // When a movie with the same id is inserted
        val newMovie = Movie(
            DEFAULT_IS_FAVORITE,
            DEFAULT_TITLE,
            DEFAULT_YEAR,
            DEFAULT_PLOT,
            NEW_POSTER,
            DEFAULT_RUNTIME,
            DEFAULT_GENRE,
            DEFAULT_DIRECTOR,
            DEFAULT_WRITER,
            DEFAULT_ACTORS,
            DEFAULT_IMDB_ID
        ).apply {
            isFavorite = NEW_IS_FAVORITE
        }
        database.moviesDao().insertMovie(newMovie)

        // When getting the movie by id from the database
        val loaded = database.moviesDao().getMovieByImdbId(DEFAULT_MOVIE.imdbId)

        // The loaded data contains the expected values
        assertMovie(loaded, DEFAULT_IMDB_ID, DEFAULT_TITLE, DEFAULT_YEAR, NEW_POSTER, NEW_IS_FAVORITE)
    }

    @Test fun insertMovieAndGetMovies() {
        // When inserting a movie
        database.moviesDao().insertMovie(DEFAULT_MOVIE)

        // When getting the movies from the database
        val movies = database.moviesDao().getMovies()

        // There is only 1 movie in the database
        MatcherAssert.assertThat(movies.size, CoreMatchers.`is`(1))
        // The loaded data contains the expected values
        assertMovie(movies[0], DEFAULT_IMDB_ID, DEFAULT_TITLE, DEFAULT_YEAR, DEFAULT_POSTER, DEFAULT_IS_FAVORITE)
    }

    @Test fun updateMovieAndGetByImdbId() {
        // When inserting a movie
        database.moviesDao().insertMovie(DEFAULT_MOVIE)

        // When the movie is updated
        val updatedMovie = Movie(
            DEFAULT_IS_FAVORITE,
            DEFAULT_TITLE,
            DEFAULT_YEAR,
            DEFAULT_PLOT,
            NEW_POSTER,
            DEFAULT_RUNTIME,
            DEFAULT_GENRE,
            DEFAULT_DIRECTOR,
            DEFAULT_WRITER,
            DEFAULT_ACTORS,
            DEFAULT_IMDB_ID
        ).apply {
            isFavorite = NEW_IS_FAVORITE
        }
        database.moviesDao().updateMovie(updatedMovie)

        // When getting the movie by id from the database
        val loaded = database.moviesDao().getMovieByImdbId(DEFAULT_IMDB_ID)

        // The loaded data contains the expected values
        assertMovie(loaded, DEFAULT_IMDB_ID, DEFAULT_TITLE, DEFAULT_YEAR, NEW_POSTER, NEW_IS_FAVORITE)
    }

    @Test fun deleteMovieByIdAndGettingMovies() {
        // Given a movie inserted
        database.moviesDao().insertMovie(DEFAULT_MOVIE)

        // When deleting a movie by id
        database.moviesDao().deleteMobieByImdbID(DEFAULT_MOVIE.imdbId)

        // When getting the movies
        val movies = database.moviesDao().getMovies()

        // The list is empty
        MatcherAssert.assertThat(movies.size, CoreMatchers.`is`(0))
    }

    @Test fun deleteMoviesAndGettingMovies() {
        // Given a movie inserted
        database.moviesDao().insertMovie(DEFAULT_MOVIE)

        // When deleting all movies
        database.moviesDao().deleteMovies()

        // When getting the movies
        val movies = database.moviesDao().getMovies()

        // The list is empty
        MatcherAssert.assertThat(movies.size, CoreMatchers.`is`(0))
    }

    private fun assertMovie(
        movie: Movie?,
        imdbId: String,
        title: String,
        year: String,
        poster: String,
        isFavorite: Boolean
    ) {
        MatcherAssert.assertThat<Movie>(movie as Movie, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(movie.imdbId, CoreMatchers.`is`(imdbId))
        MatcherAssert.assertThat(movie.title, CoreMatchers.`is`(title))
        MatcherAssert.assertThat(movie.year, CoreMatchers.`is`(year))
        MatcherAssert.assertThat(movie.poster, CoreMatchers.`is`(poster))
        MatcherAssert.assertThat(movie.isFavorite, CoreMatchers.`is`(isFavorite))
    }

    companion object {

        private val DEFAULT_IS_FAVORITE = false
        private val DEFAULT_TITLE = "title"
        private val DEFAULT_YEAR = "year"
        private val DEFAULT_PLOT = "plot"
        private val DEFAULT_POSTER = "poster"
        private val DEFAULT_RUNTIME = "runtime"
        private val DEFAULT_GENRE = "genre"
        private val DEFAULT_DIRECTOR = "director"
        private val DEFAULT_WRITER = "writer"
        private val DEFAULT_ACTORS = "actors"
        private val DEFAULT_IMDB_ID = "imdbID"

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

        private val NEW_IS_FAVORITE = true
        private val NEW_POSTER = "new poster"
    }
}