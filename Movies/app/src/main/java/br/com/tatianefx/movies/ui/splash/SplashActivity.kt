package br.com.tatianefx.movies.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.tatianefx.movies.R
import br.com.tatianefx.movies.ui.favorites.FavoritesActivity

/**
 * Created by Tatiane Souza on 15/03/2019.
 */

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val timer = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(SPLASH_TIME)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    startActivity(FavoritesActivity.newIntent(this@SplashActivity))
                    finish()
                }
            }
        }
        timer.start()
    }
}
