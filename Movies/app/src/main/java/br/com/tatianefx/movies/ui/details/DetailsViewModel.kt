package br.com.tatianefx.movies.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.tatianefx.movies.data.MovieDetail
import br.com.tatianefx.movies.network.ApiClient
import br.com.tatianefx.movies.network.OnResponseListener
import okhttp3.ResponseBody

/**
 * Created by Tatiane Souza on 14/03/2019.
 */

class DetailsViewModel : ViewModel(), OnResponseListener<MovieDetail> {

    private val _detail = MutableLiveData<MovieDetail>()
    val detail: LiveData<MovieDetail>
        get() = _detail

    fun start(imdbId: String?) {
        imdbId?.let {
            ApiClient.getMovieDetails(it, this)
        }
    }

    //region Callback Methods

    override fun onSuccess(response: MovieDetail) {
        _detail.value = response
    }

    override fun onError(body: ResponseBody, code: Int) {

    }

    override fun onFailure(str: String) {

    }

    override fun noInternet() {

    }

    //endregion
}
