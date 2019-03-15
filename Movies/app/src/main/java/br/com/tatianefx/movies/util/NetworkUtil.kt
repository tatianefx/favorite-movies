package br.com.tatianefx.movies.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by Tatiane Souza on 15/03/2019.
 */

object NetworkUtil {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
}