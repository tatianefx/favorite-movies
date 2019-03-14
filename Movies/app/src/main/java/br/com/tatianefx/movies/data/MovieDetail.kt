package br.com.tatianefx.movies.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Tatiane Souza on 14/03/2019.
 */

data class MovieDetail constructor(

    @SerializedName("Title")
    var title: String = "",

    @SerializedName("Year")
    var year: String = "",

    @SerializedName("imdbID")
    var imdbId: String = "",

    @SerializedName("Plot")
    var plot: String = "",

    @SerializedName("Poster")
    var poster: String = "",

    @SerializedName("Runtime")
    var runtime: String = "",

    @SerializedName("Genre")
    var genre: String = "",

    @SerializedName("Director")
    var director: String = "",

    @SerializedName("Writer")
    var writer: String = "",

    @SerializedName("Actors")
    var actors: String = ""
)