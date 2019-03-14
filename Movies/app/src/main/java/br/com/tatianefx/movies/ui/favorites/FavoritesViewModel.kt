package br.com.tatianefx.movies.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.tatianefx.movies.R
import br.com.tatianefx.movies.ui.common.MoviesAdapter
import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.data.source.MoviesDataSource
import br.com.tatianefx.movies.data.source.MoviesRepository
import br.com.tatianefx.movies.util.Event


/**
 * Created by Tatiane Souza on 12/03/2019.
 */

class FavoritesViewModel(private val moviesRepository: MoviesRepository): ViewModel(), MoviesDataSource.LoadMoviesCallback {

    //region Attributes

    private var items: List<Movie> = emptyList()

    private val _adapter = MutableLiveData<MoviesAdapter<FavoritesViewModel>>()
    val adapter: LiveData<MoviesAdapter<FavoritesViewModel>>
        get() = _adapter

    private val _noFavoriteMoviesVisibility = MutableLiveData<Int>()
    val noFavoriteMoviesVisibility: LiveData<Int>
        get() = _noFavoriteMoviesVisibility

    private val _addNewMovieEvent = MutableLiveData<Event<Unit>>()
    val addNewMovieEvent: LiveData<Event<Unit>>
        get() = _addNewMovieEvent

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
        items = movies
    }

    override fun onDataNotAvailable() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //endregion
}
