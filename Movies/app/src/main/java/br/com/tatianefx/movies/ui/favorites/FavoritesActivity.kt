package br.com.tatianefx.movies.ui.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import br.com.tatianefx.movies.R
import br.com.tatianefx.movies.ui.addmovie.AddMovieActivity
import br.com.tatianefx.movies.util.Event
import br.com.tatianefx.movies.util.obtainViewModel
import br.com.tatianefx.movies.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.favorites_activity.*

/**
 * Created by Tatiane Souza on 12/03/2019.
 */

class FavoritesActivity : AppCompatActivity(), FavoritesNavigator {

    private lateinit var viewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorites_activity)

        setSupportActionBar(toolbar)

        setupViewFragment()

        viewModel = obtainViewModel().apply {
            // Subscribe to "add movie" event
            addNewMovieEvent.observe(this@FavoritesActivity, Observer<Event<Unit>> { event ->
                event.getContentIfNotHandled()?.let {
                    this@FavoritesActivity.addNewMovie()
                }
            })
        }
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
