package br.com.tatianefx.movies.ui.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import br.com.tatianefx.movies.R
import br.com.tatianefx.movies.util.Event
import br.com.tatianefx.movies.util.obtainViewModel
import br.com.tatianefx.movies.util.replaceFragmentInActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.details_activity.*

/**
 * Created by Tatiane Souza on 14/03/2019.
 */

class DetailsActivity : AppCompatActivity(), DetailsNavigator {

    private lateinit var viewModel: DetailsViewModel
    private var menuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imdbId = intent?.extras?.getString(EXTRA_IMDB_ID)
        setupViewFragment(imdbId)

        viewModel = obtainViewModel().apply {
            onFailureEvent.observe(this@DetailsActivity, Observer<Event<String>> { event ->
                event.getContentIfNotHandled()?.let {
                    this@DetailsActivity.onFailure(it)
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_favorite, menu)

        menuItem = menu.findItem(R.id.action_favorite)
        viewModel.isFavorite.value?.let {
            updateFavoriteIcon(it)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
             R.id.action_favorite -> {
                 viewModel.isFavorite.value?.let {
                     updateFavoriteIcon(!it)
                 }
                 viewModel.updateFavorite()
             }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFailure(message: String) {
        Snackbar.make(details_activity, message, Snackbar.LENGTH_LONG).show()
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            menuItem?.setIcon(R.drawable.ic_favorite_24)
        } else {
            menuItem?.setIcon(R.drawable.ic_favorite_border_24)
        }
    }

    private fun setupViewFragment(imdbId: String?) {
        supportFragmentManager.findFragmentById(R.id.container)
            ?: replaceFragmentInActivity(DetailsFragment.newInstance(imdbId), R.id.container)
    }

    internal fun obtainViewModel(): DetailsViewModel = obtainViewModel(DetailsViewModel::class.java)

    companion object {

        private const val EXTRA_IMDB_ID = "IMDB_ID"

        fun newIntent(context: Context, imdbId: String): Intent {
            return Intent(context, DetailsActivity::class.java).apply {
                putExtra(DetailsActivity.EXTRA_IMDB_ID, imdbId)
            }
        }
    }
}
