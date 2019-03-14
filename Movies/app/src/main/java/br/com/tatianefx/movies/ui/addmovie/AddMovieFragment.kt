package br.com.tatianefx.movies.ui.addmovie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.tatianefx.movies.databinding.AddMovieFragmentBinding

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

class AddMovieFragment : Fragment() {

    private lateinit var viewDataBinding: AddMovieFragmentBinding

    companion object {
        fun newInstance() = AddMovieFragment()
    }

    private lateinit var viewModel: AddMovieViewModel

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
    }


}
