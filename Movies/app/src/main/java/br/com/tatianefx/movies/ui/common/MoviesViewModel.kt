package br.com.tatianefx.movies.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.util.Event

/**
 * Created by Tatiane Souza on 14/03/2019.
 */

open class MoviesViewModel: ViewModel() {

    var items: ArrayList<Movie> = arrayListOf()

    private val _openMovieEvent = MutableLiveData<Event<String>>()
    val openMovieEvent: LiveData<Event<String>>
        get() = _openMovieEvent

    /**
     * Called by the [MoviesAdapter].
     */
    internal fun openMovie(imdbId: String) {
        _openMovieEvent.value = Event(imdbId)
    }
}