package com.moviedb.ui.details

import com.moviedb.data.model.MovieDetailData
import com.moviedb.domain.interactors.GetMovieInteractor
import com.moviedb.domain.usecase.GetMovieUseCase
import com.moviedb.domain.usecase.UseCase
import dagger.Module
import dagger.Provides

@Module
class MovieDetailsModule {

    @Provides
    fun provideGetMovieUseCase(useCase: GetMovieUseCase): UseCase<GetMovieInteractor, MovieDetailData> {
        return useCase
    }

    @Provides
    fun provideViewModelFactory(useCase: UseCase<GetMovieInteractor, MovieDetailData>): MovieDetailsViewModelFactory {
        return MovieDetailsViewModelFactory(useCase)
    }
}