package br.com.tatianefx.movies.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.tatianefx.movies.common.MoviesAdapter
import br.com.tatianefx.movies.data.Movie

/**
 * Created by Tatiane Souza on 12/03/2019.
 */

class FavoritesViewModel(application: Application): AndroidViewModel(application) {

    private val _adapter = MutableLiveData<MoviesAdapter>()
    val adapter: LiveData<MoviesAdapter>
        get() = _adapter

    private val _items = MutableLiveData<List<Movie>>().apply { value = emptyList() }
    val items: LiveData<List<Movie>>
        get() = _items

    private val _recyclerViewVisibility = MutableLiveData<Int>()
    val recyclerViewVisibility: LiveData<Int>
        get() = _recyclerViewVisibility

    private val _noFavoriteMoviesVisibility = MutableLiveData<Int>()
    val noFavoriteMoviesVisibility: LiveData<Int>
        get() = _noFavoriteMoviesVisibility
}
