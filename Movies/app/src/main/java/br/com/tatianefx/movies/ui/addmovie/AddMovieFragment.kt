package br.com.tatianefx.movies.ui.addmovie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tatianefx.movies.databinding.AddMovieFragmentBinding
import kotlinx.android.synthetic.main.add_movie_fragment.*

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

class AddMovieFragment : Fragment() {

    private lateinit var viewDataBinding: AddMovieFragmentBinding

    companion object {
        fun newInstance() = AddMovieFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = AddMovieFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as AddMovieActivity).obtainViewModel()
        }
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewModel?.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupRecyclerAdapter()
        setupNestedScroll()
    }

    private fun setupRecyclerAdapter() {
        viewDataBinding.addMoviesRecyclerview.layoutManager = LinearLayoutManager(context)
    }

    private fun setupNestedScroll() {
        add_movies_nested_scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            onScrollBottom(v, scrollY)
        })
    }

    private fun onScrollBottom(v: NestedScrollView, scrollY: Int) {
        if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
            context?.let {
                viewDataBinding.viewModel?.loadNextPage(it)
            }
        }
    }
}
