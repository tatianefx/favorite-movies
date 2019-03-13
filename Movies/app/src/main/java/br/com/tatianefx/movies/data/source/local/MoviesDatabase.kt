package br.com.tatianefx.movies.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.tatianefx.movies.data.Movie


/**
 * Created by Tatiane Souza on 12/03/2019.
 */


/**
 * The Room Database that contains the Movies table.
 */
@Database(entities = [Movie::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object {

        private var INSTANCE: MoviesDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): MoviesDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MoviesDatabase::class.java, "Movies.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }

}