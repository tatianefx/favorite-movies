package br.com.tatianefx.movies.ui.addmovie

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.tatianefx.movies.R
import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.data.source.MoviesDataSource
import br.com.tatianefx.movies.data.source.MoviesRepository
import br.com.tatianefx.movies.network.ApiClient
import br.com.tatianefx.movies.network.OnResponseListener
import br.com.tatianefx.movies.ui.common.MoviesAdapter
import br.com.tatianefx.movies.ui.common.MoviesViewModel
import br.com.tatianefx.movies.util.Event
import okhttp3.ResponseBody

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

class AddMovieViewModel(private val moviesRepository: MoviesRepository) : MoviesViewModel(), MoviesDataSource.LoadMoviesCallback {

    //region Attributes

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int>
        get() = _progressVisibility

    private val _searchMovieEvent = MutableLiveData<Event<Unit>>()
    val searchMovieEvent: LiveData<Event<Unit>>
        get() = _searchMovieEvent

    private val _adapter = MutableLiveData<MoviesAdapter<AddMovieViewModel>>()
    val adapter: LiveData<MoviesAdapter<AddMovieViewModel>>
        get() = _adapter

    private val _recyclerViewVisibility = MutableLiveData<Int>()
    val recyclerViewVisibility: LiveData<Int>
        get() = _recyclerViewVisibility

    private val _messageVisibility = MutableLiveData<Int>()
    val messageVisibility: LiveData<Int>
        get() = _messageVisibility

    private val _noMoviesVisibility = MutableLiveData<Int>()
    val noMoviesVisibility: LiveData<Int>
        get() = _noMoviesVisibility

    private val _onFailureEvent = MutableLiveData<Event<String>>()
    val onFailureEvent: LiveData<Event<String>>
        get() = _onFailureEvent

    //endregion

    init {
        _noMoviesVisibility.value = View.GONE
        _adapter.value = MoviesAdapter(this, R.layout.add_movie_item)
    }

    //region Public Methods

    fun start() {
        //TODO
    }

    fun searchMovie(title: String) {
        _progressVisibility.value = View.VISIBLE
        moviesRepository.searchMovies(title, this)
    }

    fun getMovieAt(position: Int): Movie? {
        return items.getOrNull(position)
    }

    //endregion

    //region Private Methods

    private fun updateVisibility() {
        _messageVisibility.value = View.GONE
        if (items.isEmpty()) {
            _noMoviesVisibility.value = View.VISIBLE
            _recyclerViewVisibility.value = View.GONE
        } else {
            _noMoviesVisibility.value = View.GONE
            _recyclerViewVisibility.value = View.VISIBLE
        }
    }

    //endregion

    //region Callback Methods

    override fun onMoviesLoaded(movies: List<Movie>) {
        items = movies
        _adapter.value?.notifyDataSetChanged()
        _progressVisibility.value = View.GONE
        updateVisibility()
        _searchMovieEvent.value = Event(Unit)
    }

    override fun onDataNotAvailable() {
        _progressVisibility.value = View.GONE
        _onFailureEvent.value = Event("Data not available")
    }

    override fun onFaliure(message: String) {
        _progressVisibility.value = View.GONE
        _onFailureEvent.value = Event(message)
    }

    //endregion
}
