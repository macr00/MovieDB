package com.moviedb.data

import com.moviedb.domain.MovieRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor({
                    val original = it.request()
                    val originalUrl = original.url()
                    val newUrl = originalUrl
                            .newBuilder()
                            .addQueryParameter("api_key", "ba8d9706357dbc44dc79f9a12c2d4aa1")
                            .build()
                    val request = original
                            .newBuilder()
                            .url(newUrl)
                            .build()
                    it.proceed(request)
                })
                .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // Add custom GSON
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): MovieDatabaseApi {
        return retrofit.create(MovieDatabaseApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(movieRepository: MovieRepositoryImpl): MovieRepository {
        return movieRepository
    }

    companion object {
        val BASE_URL = "https://api.themoviedb.org/3/"
    }
}