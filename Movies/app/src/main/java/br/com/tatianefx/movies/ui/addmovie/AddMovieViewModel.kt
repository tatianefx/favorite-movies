package br.com.tatianefx.movies.ui.addmovie

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.network.ApiClient
import br.com.tatianefx.movies.network.OnResponseListener
import br.com.tatianefx.movies.util.Event
import okhttp3.ResponseBody


/**
 * Created by Tatiane Souza on 13/03/2019.
 */

class AddMovieViewModel : ViewModel(), OnResponseListener<List<Movie>> {

    private val _items = MutableLiveData<List<Movie>>().apply { value = emptyList() }
    val items: LiveData<List<Movie>>
        get() = _items

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int>
        get() = _progressVisibility

    private val _searchMovieEvent = MutableLiveData<Event<Unit>>()
    val searchMovieEvent: LiveData<Event<Unit>>
        get() = _searchMovieEvent

    fun searchMovie(title: String) {
        _progressVisibility.value = View.VISIBLE
        ApiClient.getMovieByTitle(title, this)
    }

    override fun onSuccess(response: List<Movie>) {
        _items.value = response
        _progressVisibility.value = View.GONE
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
}
