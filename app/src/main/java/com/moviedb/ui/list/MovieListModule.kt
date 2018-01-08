package com.moviedb.ui.list


import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.GetMovieListInteractor
import com.moviedb.domain.MovieRepository
import com.moviedb.domain.SearchMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import com.moviedb.domain.usecase.GetMovieListUseCase
import com.moviedb.domain.usecase.SearchMoviesUseCase
import com.moviedb.domain.usecase.UseCase
import dagger.Module
import dagger.Provides

@Module
class MovieListModule {

    @Provides
    fun provideGetAllUseCase(useCase: GetMovieListUseCase): UseCase<GetMovieListInteractor, MovieListResponseData> {
        return useCase
    }

    @Provides
    fun provideSearchUseCase(useCase: SearchMoviesUseCase): UseCase<SearchMoviesInteractor, MovieListResponseData> {
        return useCase
    }

    @Provides
    fun provideViewModelFactory(
            getAllUseCase: UseCase<GetMovieListInteractor, MovieListResponseData>,
            searchUseCase: UseCase<SearchMoviesInteractor, MovieListResponseData>,
            schedulers: RxSchedulers
    ): MovieListViewModelFactory {
        return MovieListViewModelFactory(getAllUseCase, searchUseCase, schedulers)
    }

}