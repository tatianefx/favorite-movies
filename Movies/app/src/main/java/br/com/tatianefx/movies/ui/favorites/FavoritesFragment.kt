package br.com.tatianefx.movies.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tatianefx.movies.R
import br.com.tatianefx.movies.databinding.FavoritesFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Created by Tatiane Souza on 12/03/2019.
 */

class FavoritesFragment : Fragment() {

    private lateinit var viewDataBinding: FavoritesFragmentBinding

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = FavoritesFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as FavoritesActivity).obtainViewModel()
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewModel?.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupFab()
        setupRecyclerAdapter()
    }

    private fun setupFab() {
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.let {
            it.setOnClickListener {
                viewDataBinding.viewModel?.addNewMovie()
            }
        }
    }

    private fun setupRecyclerAdapter() {
        viewDataBinding.favoritesRecyclerview.layoutManager = LinearLayoutManager(context)
    }
}
