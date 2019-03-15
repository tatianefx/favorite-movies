package br.com.tatianefx.movies.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.tatianefx.movies.databinding.DetailsFragmentBinding

/**
 * Created by Tatiane Souza on 14/03/2019.
 */

class DetailsFragment : Fragment() {

    private lateinit var viewDataBinding: DetailsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = DetailsFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as DetailsActivity).obtainViewModel()
        }
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            viewDataBinding.viewModel?.start(it, arguments?.getString(ARGUMENT_IMDB_ID))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
    }

    companion object {

        private const val ARGUMENT_IMDB_ID = "IMDB_ID"

        fun newInstance(imadId: String?) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_IMDB_ID, imadId)
            }
        }
    }
}
