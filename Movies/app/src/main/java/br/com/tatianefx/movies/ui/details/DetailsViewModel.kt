package br.com.tatianefx.movies.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.data.source.MoviesDataSource
import br.com.tatianefx.movies.data.source.MoviesRepository
import br.com.tatianefx.movies.util.Event

/**
 * Created by Tatiane Souza on 14/03/2019.
 */

class DetailsViewModel(private val moviesRepository: MoviesRepository) : ViewModel(), MoviesDataSource.GetMovieCallback {

    //region Attributes

    private var movieImdbId: String? = null

    private val _detail = MutableLiveData<Movie>()
    val detail: LiveData<Movie>
        get() = _detail

    private val _onFailureEvent = MutableLiveData<Event<String>>()
    val onFailureEvent: LiveData<Event<String>>
        get() = _onFailureEvent

    //endregion

    //region Public Methods

    fun start(imdbId: String?) {
        imdbId?.let {
            movieImdbId = it
            moviesRepository.getMovie(it, this)
        }
    }

    fun updateFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            saveMovie()
        } else {
            deleteMovie()
        }
    }

    //endregion

    //region Public Methods

    private fun saveMovie() {
        detail.value?.let {
            moviesRepository.saveMovie(it)
        }
    }

    private fun deleteMovie() {
        detail.value?.let {
            moviesRepository.deleteMovie(it.imdbId)
        }
    }

    //endregion

    //region Override Methods

    override fun onMovieLoaded(movie: Movie) {
        _detail.value = movie
    }

    override fun onDataNotAvailable() {
        _onFailureEvent.value = Event("Data not available")
    }

    override fun onFaliure(message: String) {
        _onFailureEvent.value = Event(message)
    }

    //endregion
}
