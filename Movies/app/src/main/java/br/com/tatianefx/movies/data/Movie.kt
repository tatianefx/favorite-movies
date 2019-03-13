package br.com.tatianefx.movies.data

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


/**
 * Created by Tatiane Souza on 12/03/2019.
 */

@Entity(tableName = "movies")
data class Movie @JvmOverloads constructor(

    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "plot") var plot: String = "",
    @ColumnInfo(name = "poster") var poster: String = "",
    @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString()

) {
    companion object {

        val DiffCallback = object : DiffUtil.ItemCallback<Movie>() {

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean
                    = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean
                    = oldItem.id == newItem.id
                    && oldItem.title == newItem.title
                    && oldItem.plot == newItem.plot
                    && oldItem.poster == newItem.poster
        }
    }
}