package com.moviedb.ui.list


import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.GetMovieListInteractor
import com.moviedb.domain.MovieRepository
import com.moviedb.domain.SearchMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import com.moviedb.domain.usecase.GetMovieListUseCase
import com.moviedb.domain.usecase.SearchMoviesUseCase
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.common.MovieResultsPaginator
import dagger.Module
import dagger.Provides

@Module
class MovieListModule {

    @Provides
    fun provideGetAllUseCase(useCase: GetMovieListUseCase): UseCase<GetMovieListInteractor, MovieListResponseData> {
        return useCase
    }

    @Provides
    fun provideViewModelFactory(
            getAllUseCase: UseCase<GetMovieListInteractor, MovieListResponseData>,
            paginator: MovieResultsPaginator
    ): MovieListViewModelFactory {
        return MovieListViewModelFactory(getAllUseCase, paginator)
    }

}