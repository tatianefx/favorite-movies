package br.com.tatianefx.movies.ui.favorites

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.tatianefx.movies.R
import br.com.tatianefx.movies.ui.common.MoviesAdapter
import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.data.source.MoviesDataSource
import br.com.tatianefx.movies.data.source.MoviesRepository
import br.com.tatianefx.movies.ui.common.MoviesViewModel
import br.com.tatianefx.movies.util.Event


/**
 * Created by Tatiane Souza on 12/03/2019.
 */

class FavoritesViewModel(private val moviesRepository: MoviesRepository): MoviesViewModel(), MoviesDataSource.LoadMoviesCallback {

    //region Attributes

    private val _adapter = MutableLiveData<MoviesAdapter<FavoritesViewModel>>()
    val adapter: LiveData<MoviesAdapter<FavoritesViewModel>>
        get() = _adapter

    private val _noFavoriteMoviesVisibility = MutableLiveData<Int>()
    val noFavoriteMoviesVisibility: LiveData<Int>
        get() = _noFavoriteMoviesVisibility

    private val _addNewMovieEvent = MutableLiveData<Event<Unit>>()
    val addNewMovieEvent: LiveData<Event<Unit>>
        get() = _addNewMovieEvent

    private val _onFailureEvent = MutableLiveData<Event<String>>()
    val onFailureEvent: LiveData<Event<String>>
        get() = _onFailureEvent

    //endregion

    init {
        _adapter.value = MoviesAdapter(this, R.layout.favorite_movies_item)
    }

    //region Public Methods

    fun start() {
        loadLocalMovies()
    }

    /**
     * Called by the Data Binding library and the FAB's click listener.
     */
    fun addNewMovie() {
        _addNewMovieEvent.value = Event(Unit)
    }

    fun getMovieAt(position: Int): Movie? {
        return items.getOrNull(position)
    }

    //endregion

    //region Private Methods

    private fun loadLocalMovies() {
        moviesRepository.getMovies(this)
    }

    //endregion

    //region Override Methods

    override fun onMoviesLoaded(movies: List<Movie>) {
        items.clear()
        items.addAll(movies)
        _adapter.value?.notifyDataSetChanged()
        _noFavoriteMoviesVisibility.value = View.GONE
    }

    override fun onDataNotAvailable() {
        _noFavoriteMoviesVisibility.value = View.VISIBLE
    }

    override fun onFailure(message: String) {
        _onFailureEvent.value = Event(message)
    }

    //endregion
}
