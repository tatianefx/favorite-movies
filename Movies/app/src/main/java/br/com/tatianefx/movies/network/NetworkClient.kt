package br.com.tatianefx.movies.network

import br.com.tatianefx.movies.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import br.com.tatianefx.movies.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException


/**
 * Created by Tatiane Souza on 13/03/2019.
 */

object NetworkClient {

    private val retrofit: Retrofit by lazy {
        getRetrofitInstance(Constants.MOVIE_BASE_URL)
    }

    fun getApi(): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    private fun getRetrofitInstance(path : String) : Retrofit {

        // Interceptor
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val originalHttpUrl = original.url()

                // Add api key
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(Constants.API_KEY, BuildConfig.API_KEY)
                    .build()

                // Request customization: add request headers
                val requestBuilder = original.newBuilder()
                    .url(url)

                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })

        return Retrofit.Builder()
            .baseUrl(path)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }
}