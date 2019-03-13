package br.com.tatianefx.movies.network

import okhttp3.ResponseBody

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

interface OnResponseListener<T> {

    fun onSuccess(response: T)

    fun onError(body: ResponseBody, code: Int)

    fun onFailure(str: String)

    fun noInternet()
}