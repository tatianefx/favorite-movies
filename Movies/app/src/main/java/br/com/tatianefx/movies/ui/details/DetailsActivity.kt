package br.com.tatianefx.movies.ui.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.tatianefx.movies.R

/**
 * Created by Tatiane Souza on 14/03/2019.
 */

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailsFragment.newInstance())
                .commitNow()
        }
    }

    companion object {

        private const val EXTRA_IMDB_ID = "IMDB_ID"

        fun newIntent(context: Context, imdbId: String): Intent {
            return Intent(context, DetailsActivity::class.java).apply {
                putExtra(DetailsActivity.EXTRA_IMDB_ID, imdbId)
            }
        }
    }
}
