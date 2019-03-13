package br.com.tatianefx.movies.util

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.tatianefx.movies.data.source.MoviesRepository
import br.com.tatianefx.movies.ui.addmovie.AddMovieViewModel
import br.com.tatianefx.movies.ui.favorites.FavoritesViewModel

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

class ViewModelFactory private constructor(
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(FavoritesViewModel::class.java) ->
                    FavoritesViewModel(moviesRepository)
                isAssignableFrom(AddMovieViewModel::class.java) ->
                    AddMovieViewModel()
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideMoviesRepository(application.applicationContext))
                    .also { INSTANCE = it }
            }


        @VisibleForTesting fun destroyInstance() {
            INSTANCE = null
        }
    }
}
