package br.com.tatianefx.movies.data

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*


/**
 * Created by Tatiane Souza on 12/03/2019.
 */

@Entity(tableName = "movies")
data class Movie @JvmOverloads constructor(

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,

    @SerializedName("Title")
    @ColumnInfo(name = "title")
    var title: String = "",

    @SerializedName("Year")
    @ColumnInfo(name = "year")
    var year: String = "",

    @SerializedName("Plot")
    @ColumnInfo(name = "plot")
    var plot: String = "",

    @SerializedName("Poster")
    @ColumnInfo(name = "poster")
    var poster: String = "",

    @SerializedName("Runtime")
    @ColumnInfo(name = "runtime")
    var runtime: String = "",

    @SerializedName("Genre")
    @ColumnInfo(name = "genre")
    var genre: String = "",

    @SerializedName("Director")
    @ColumnInfo(name = "director")
    var director: String = "",

    @SerializedName("Writer")
    @ColumnInfo(name = "writer")
    var writer: String = "",

    @SerializedName("Actors")
    @ColumnInfo(name = "actors")
    var actors: String = "",

    @PrimaryKey
    @SerializedName("imdbID")
    @ColumnInfo(name = "imdbID")
    var imdbId: String
) {
    companion object {

        val DiffCallback = object : DiffUtil.ItemCallback<Movie>() {

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean
                    = oldItem.imdbId == newItem.imdbId

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean
                    = oldItem.imdbId == newItem.imdbId
                    && oldItem.title == newItem.title
                    && oldItem.year == newItem.year
                    && oldItem.plot == newItem.plot
                    && oldItem.poster == newItem.poster
                    && oldItem.runtime == newItem.runtime
                    && oldItem.genre == newItem.genre
                    && oldItem.director == newItem.director
                    && oldItem.writer == newItem.writer
                    && oldItem.actors == newItem.actors
        }
    }
}