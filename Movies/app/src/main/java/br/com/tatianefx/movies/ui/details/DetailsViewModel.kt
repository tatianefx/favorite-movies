package br.com.tatianefx.movies.ui.details

import android.content.Context
import android.view.View
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

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val _onFailureEvent = MutableLiveData<Event<String>>()
    val onFailureEvent: LiveData<Event<String>>
        get() = _onFailureEvent

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int>
        get() = _progressVisibility

    private val _viewVisibility = MutableLiveData<Int>()
    val viewVisibility: LiveData<Int>
        get() = _viewVisibility

    //endregion

    //region Public Methods

    fun start(context: Context, imdbId: String?) {
        _progressVisibility.value = View.VISIBLE
        imdbId?.let {
            movieImdbId = it
            moviesRepository.getMovie(context, it, this)
        }
    }

    fun updateFavorite() {
        _detail.value?.isFavorite?.let {
            if (!it) {
                saveMovie()
            } else {
                deleteMovie()
            }
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
            _isFavorite.value = false
        }
    }

    //endregion

    //region Override Methods

    override fun onMovieLoaded(movie: Movie) {
        _detail.value = movie
        _isFavorite.value = movie.isFavorite
        _progressVisibility.value = View.GONE
        _viewVisibility.value = View.VISIBLE
    }

    override fun onDataNotAvailable() {
        _onFailureEvent.value = Event("Data not available")
        _progressVisibility.value = View.GONE
        _viewVisibility.value = View.GONE
    }

    override fun onFaliure(message: String) {
        _onFailureEvent.value = Event(message)
        _progressVisibility.value = View.GONE
        _viewVisibility.value = View.GONE
    }

    //endregion
}
