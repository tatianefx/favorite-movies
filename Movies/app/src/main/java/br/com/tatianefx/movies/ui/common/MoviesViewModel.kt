package br.com.tatianefx.movies.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * Created by Tatiane Souza on 12/03/2019.
 */


class MoviesViewModel: ViewModel() {

    private val _poster = MutableLiveData<String>()
    val image: LiveData<String>
        get() = _poster

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _year = MutableLiveData<String>()
    val year: LiveData<String>
        get() = _year
}