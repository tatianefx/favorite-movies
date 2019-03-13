package br.com.tatianefx.movies.data.source.local

import androidx.paging.DataSource
import androidx.room.*
import br.com.tatianefx.movies.data.Movie


/**
 * Created by Tatiane Souza on 12/03/2019.
 */

/**
 * Data Access Object for the movies table.
 */
@Dao interface MoviesDao {

    /**
     * Select all movies from the movies table.
     *
     * @return all movies.
     */
    @Query("SELECT * FROM Movies") fun getMovies(): List<Movie>

    /**
     * Select all movies pagedfrom the movies table.
     *
     * @return all movies paged.
     */
    @Query("SELECT * FROM Movies") fun getAllMoviesPaged(): DataSource.Factory<Int, Movie>

    /**
     * Select a movie by id.
     *
     * @param id the movie id.
     * @return the movie with idd.
     */
    @Query("SELECT * FROM Movies WHERE id = :id") fun getMovieById(id: String): Movie?

    /**
     * Insert a movie in the database. If the movie already exists, replace it.
     *
     * @param movie the movie to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertMovie(movie: Movie)

    /**
     * Update a movie.
     *
     * @param movie movie to be updated
     * @return the number of movies updated. This should always be 1.
     */
    @Update fun updateMovie(movie: Movie): Int

    /**
     * Delete a movie by id.
     *
     * @return the number of movies deleted. This should always be 1.
     */
    @Query("DELETE FROM movies WHERE id = :id") fun deleteMobieById(id: String): Int

    /**
     * Delete all movies.
     */
    @Query("DELETE FROM Movies") fun deleteMovies()

}