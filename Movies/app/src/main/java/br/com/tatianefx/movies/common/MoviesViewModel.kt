package br.com.tatianefx.movies.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


/**
 * Created by Tatiane Souza on 12/03/2019.
 */


class MoviesViewModel(application: Application): AndroidViewModel(application) {

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