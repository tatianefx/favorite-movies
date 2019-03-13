package br.com.tatianefx.movies.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

data class Search(
    @SerializedName("Search")
    var movies: List<Movie>?
)