package br.com.tatianefx.movies.ui.common

import androidx.lifecycle.LiveData
import androidx.paging.PageKeyedDataSource
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.com.tatianefx.movies.data.Movie

/**
 * Created by tatiane.silva on 14/03/2019.
 */

class MoviesDataSourceFactory : DataSource.Factory<Int, Movie>() {

    private val _liveDataSource = MutableLiveData<PageKeyedDataSource<Int, Movie>>()
    val liveDataSource: LiveData<PageKeyedDataSource<Int, Movie>>
        get() = _liveDataSource

    private lateinit var dataSource: MoviesRemoteDataSource

    override fun create(): DataSource<Int, Movie> {

        //getting our data source object
        dataSource = MoviesRemoteDataSource()

        //posting the data source to get the values
        _liveDataSource.postValue(dataSource)

        //returning the data source
        return dataSource
    }

}