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

    @SerializedName("Title")
    @ColumnInfo(name = "title")
    var title: String = "",

    @SerializedName("Year")
    @ColumnInfo(name = "year")
    var year: String = "",

    @SerializedName("imdbID")
    @ColumnInfo(name = "imdbID")
    var imdbId: String = "",

    @SerializedName("Plot")
    @ColumnInfo(name = "plot")
    var plot: String = "",

    @SerializedName("Poster")
    @ColumnInfo(name = "poster")
    var poster: String = "",

    @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString()
) {
    companion object {

        val DiffCallback = object : DiffUtil.ItemCallback<Movie>() {

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean
                    = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean
                    = oldItem.id == newItem.id
                    && oldItem.title == newItem.title
                    && oldItem.year == newItem.year
                    && oldItem.imdbId == newItem.imdbId
                    && oldItem.plot == newItem.plot
                    && oldItem.poster == newItem.poster
        }
    }
}