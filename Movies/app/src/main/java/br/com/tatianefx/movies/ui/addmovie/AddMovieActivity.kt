package br.com.tatianefx.movies.ui.addmovie

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import br.com.tatianefx.movies.R
import br.com.tatianefx.movies.util.Event
import br.com.tatianefx.movies.util.obtainViewModel
import br.com.tatianefx.movies.util.replaceFragmentInActivity

/**
 * Created by Tatiane Souza on 13/03/2019.
 */

class AddMovieActivity : AppCompatActivity(), AddMovieNavigator, SearchView.OnQueryTextListener {

    private lateinit var viewModel: AddMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_movie_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewFragment()

        viewModel = obtainViewModel().apply {
            // Subscribe to "search movie" event
            searchMovieEvent.observe(this@AddMovieActivity, Observer<Event<Unit>> { event ->
                event.getContentIfNotHandled()?.let {
                    this@AddMovieActivity.onSearchFinished()
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

    override fun onSearchFinished() {
        //TODO
    }

    //endregion

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
