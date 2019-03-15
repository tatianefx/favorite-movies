package br.com.tatianefx.movies.ui.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import br.com.tatianefx.movies.R
import br.com.tatianefx.movies.ui.addmovie.AddMovieActivity
import br.com.tatianefx.movies.ui.common.MovieItemNavigator
import br.com.tatianefx.movies.ui.details.DetailsActivity
import br.com.tatianefx.movies.util.Event
import br.com.tatianefx.movies.util.obtainViewModel
import br.com.tatianefx.movies.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.favorites_activity.*

/**
 * Created by Tatiane Souza on 12/03/2019.
 */

class FavoritesActivity : AppCompatActivity(), FavoritesNavigator, MovieItemNavigator {

    private lateinit var viewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorites_activity)

        setSupportActionBar(toolbar)

        setupViewFragment()

        viewModel = obtainViewModel().apply {
            // Subscribe to "open movie" event
            openMovieEvent.observe(this@FavoritesActivity, Observer<Event<String>> { event ->
                event.getContentIfNotHandled()?.let {
                    this@FavoritesActivity.openMovieDetails(it)
                }
            })

            // Subscribe to "add movie" event
            addNewMovieEvent.observe(this@FavoritesActivity, Observer<Event<Unit>> { event ->
                event.getContentIfNotHandled()?.let {
                    this@FavoritesActivity.addNewMovie()
                }
            })

            onFailureEvent.observe(this@FavoritesActivity, Observer<Event<String>> { event ->
                event.getContentIfNotHandled()?.let {
                    this@FavoritesActivity.onFailure(it)
                }
            })
        }
    }

    override fun openMovieDetails(imdbId: String) {
        startActivity(DetailsActivity.newIntent(this, imdbId))
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.container)
            ?: replaceFragmentInActivity(FavoritesFragment.newInstance(), R.id.container)
    }

    override fun addNewMovie() {
        startActivity(AddMovieActivity.newIntent(this))
    }

    internal fun obtainViewModel(): FavoritesViewModel = obtainViewModel(FavoritesViewModel::class.java)
}
