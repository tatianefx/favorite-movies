package br.com.tatianefx.movies.ui.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.tatianefx.movies.R
import br.com.tatianefx.movies.util.obtainViewModel
import br.com.tatianefx.movies.util.replaceFragmentInActivity

/**
 * Created by Tatiane Souza on 14/03/2019.
 */

class DetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imdbId = intent?.extras?.getString(EXTRA_IMDB_ID)
        setupViewFragment(imdbId)

        viewModel = obtainViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
