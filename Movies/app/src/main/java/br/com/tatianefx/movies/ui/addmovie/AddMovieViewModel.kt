package br.com.tatianefx.movies.ui.addmovie

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.tatianefx.movies.R
import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.network.ApiClient
import br.com.tatianefx.movies.network.OnResponseListener
import br.com.tatianefx.movies.ui.common.MoviesAdapter
import br.com.tatianefx.movies.util.Event
import okhttp3.ResponseBody

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

class AddMovieViewModel : ViewModel(), OnResponseListener<List<Movie>> {

    //region Attributes

    private var items: List<Movie> = emptyList()

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int>
        get() = _progressVisibility

    private val _searchMovieEvent = MutableLiveData<Event<Unit>>()
    val searchMovieEvent: LiveData<Event<Unit>>
        get() = _searchMovieEvent

    private val _adapter = MutableLiveData<MoviesAdapter<AddMovieViewModel>>()
    val adapter: LiveData<MoviesAdapter<AddMovieViewModel>>
        get() = _adapter

    private val _noMoviesVisibility = MutableLiveData<Int>()
    val noMoviesVisibility: LiveData<Int>
        get() = _noMoviesVisibility

    private val _addNewMovieEvent = MutableLiveData<Event<Unit>>()
    val addNewMovieEvent: LiveData<Event<Unit>>
        get() = _addNewMovieEvent

    //endregion

    init {
        _adapter.value = MoviesAdapter(this, R.layout.add_movie_item)
    }

    //region Public Methods

    fun start() {
        //TODO
    }

    fun searchMovie(title: String) {
        _progressVisibility.value = View.VISIBLE
        ApiClient.getMovieByTitle(title, this)
    }

    fun getMovieAt(position: Int): Movie? {
        return items.getOrNull(position)
    }

    //endregion

    //region Callback Methods

    override fun onSuccess(response: List<Movie>) {
        items = response
        _progressVisibility.value = View.GONE

        if (response.isEmpty()) {
            _noMoviesVisibility.value = View.VISIBLE
        } else {
            _noMoviesVisibility.value = View.GONE
        }
        _searchMovieEvent.value = Event(Unit)
    }

    override fun onError(body: ResponseBody, code: Int) {
        _progressVisibility.value = View.GONE
    }

    override fun onFailure(str: String) {
        _progressVisibility.value = View.GONE
    }

    override fun noInternet() {
        _progressVisibility.value = View.GONE
    }

    //endregion
}
