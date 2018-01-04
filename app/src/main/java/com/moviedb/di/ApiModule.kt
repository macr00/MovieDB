package com.moviedb.di

import com.moviedb.data.MovieDatabaseApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiModule {

    fun provideApiTokenRequestInterceptor(): Interceptor {
        return Interceptor {
            val original = it.request()
            val originalUrl = original.url()
            val newUrl = originalUrl.newBuilder()
                    .addQueryParameter("api_key", "ba8d9706357dbc44dc79f9a12c2d4aa1")
                    .build()
            val request = original.newBuilder()
                    .url(newUrl)
                    .build()
            it.proceed(request)
        }
    }

    fun provideClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // Add custom GSON
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build()
    }

    fun provideApi(retrofit: Retrofit): MovieDatabaseApi {
        return retrofit.create(MovieDatabaseApi::class.java)
    }

    companion object {
        val BASE_URL = "https://api.themoviedb.org/3/"
    }
}