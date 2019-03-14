package br.com.tatianefx.movies.ui.common

import androidx.lifecycle.ViewModel
import br.com.tatianefx.movies.data.Movie

/**
 * Created by Tatiane Souza on 14/03/2019.
 */

open class MoviesViewModel: ViewModel() {

    var items: List<Movie> = emptyList()
}