package br.com.tatianefx.movies.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.tatianefx.movies.data.Movie
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import br.com.tatianefx.movies.BR
import br.com.tatianefx.movies.R


/**
 * Created by Tatiane Souza on 12/03/2019.
 */

class MoviesAdapter(private val viewModel: MoviesViewModel): PagedListAdapter<Movie, MoviesAdapter.ViewHolder>(Movie.DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)
        holder.bind(viewModel, position)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.movie_item
    }


    class ViewHolder(private val viewDataBinding: ViewDataBinding): RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bind(viewModel: MoviesViewModel, position: Int) {
            viewDataBinding.setVariable(BR.model, viewModel)
            viewDataBinding.setVariable(BR.position, position)
            viewDataBinding.executePendingBindings()
        }
    }
}