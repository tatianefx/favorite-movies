package br.com.tatianefx.movies.ui.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import br.com.tatianefx.movies.R
import br.com.tatianefx.movies.data.Movie
import br.com.tatianefx.movies.network.ApiClient
import br.com.tatianefx.movies.network.OnResponseListener
import br.com.tatianefx.movies.ui.addmovie.AddMovieActivity
import br.com.tatianefx.movies.util.Event
import br.com.tatianefx.movies.util.obtainViewModel
import br.com.tatianefx.movies.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.favorites_activity.*
import okhttp3.ResponseBody

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.container)
            ?: replaceFragmentInActivity(FavoritesFragment.newInstance(), R.id.container)
    }

    override fun addNewMovie() {
        ApiClient.getMovieByTitle("saw", object : OnResponseListener<Movie> {
            override fun onSuccess(response: Movie) {

            }

            override fun onError(body: ResponseBody, code: Int) {

            }

            override fun onFailure(str: String) {

            }

            override fun noInternet() {

            }

        })
//        startActivity(AddMovieActivity.newIntent(this))
    }

    internal fun obtainViewModel(): FavoritesViewModel = obtainViewModel(FavoritesViewModel::class.java)
}
