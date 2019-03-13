package br.com.tatianefx.movies.ui.addmovie

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.tatianefx.movies.R


/**
 * Created by Tatiane Souza on 13/03/2019.
 */

class AddMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_movie_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    AddMovieFragment.newInstance()
                )
                .commitNow()
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, AddMovieActivity::class.java)
        }
    }
}
