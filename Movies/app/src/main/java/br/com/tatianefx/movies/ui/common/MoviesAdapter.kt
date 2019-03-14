package br.com.tatianefx.movies.ui.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.tatianefx.movies.data.Movie
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import br.com.tatianefx.movies.BR


/**
 * Created by Tatiane Souza on 12/03/2019.
 */

class MoviesAdapter<T : ViewModel>(
    private val viewModel: T,
    private val itemViewType: Int):
    PagedListAdapter<Movie, MoviesAdapter.ViewHolder<T>>(Movie.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.ViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        getItem(position)
        holder.bind(viewModel, position)
    }

    override fun getItemViewType(position: Int): Int {
        return itemViewType
    }

    class ViewHolder<T : ViewModel>(private val viewDataBinding: ViewDataBinding): RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bind(viewModel: T, position: Int) {
            viewDataBinding.setVariable(BR.viewModel, viewModel)
            viewDataBinding.setVariable(BR.position, position)
            viewDataBinding.executePendingBindings()
        }
    }
}