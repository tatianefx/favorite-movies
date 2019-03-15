package br.com.tatianefx.movies.ui.addmovie

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import br.com.tatianefx.movies.R
import br.com.tatianefx.movies.ui.common.MovieItemNavigator
import br.com.tatianefx.movies.ui.details.DetailsActivity
import br.com.tatianefx.movies.util.Event
import br.com.tatianefx.movies.util.obtainViewModel
import br.com.tatianefx.movies.util.replaceFragmentInActivity

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

class AddMovieActivity : AppCompatActivity(), AddMovieNavigator, MovieItemNavigator, SearchView.OnQueryTextListener {

    private lateinit var viewModel: AddMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_movie_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewFragment()

        viewModel = obtainViewModel().apply {
            // Subscribe to "open movie" event
            openMovieEvent.observe(this@AddMovieActivity, Observer<Event<String>> { event ->
                event.getContentIfNotHandled()?.let {
                    this@AddMovieActivity.openMovieDetails(it)
                }
            })

            onFailureEvent.observe(this@AddMovieActivity, Observer<Event<String>> { event ->
                event.getContentIfNotHandled()?.let {
                    this@AddMovieActivity.onFailure(it)
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    //region Search View

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_search, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(this@AddMovieActivity)
        }

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.searchMovie(it) }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return !newText.isNullOrEmpty()
    }

    //endregion

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun openMovieDetails(imdbId: String) {
        startActivity(DetailsActivity.newIntent(this, imdbId))
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.container)
            ?: replaceFragmentInActivity(AddMovieFragment.newInstance(), R.id.container)
    }

    internal fun obtainViewModel(): AddMovieViewModel = obtainViewModel(AddMovieViewModel::class.java)

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, AddMovieActivity::class.java)
        }
    }
}
