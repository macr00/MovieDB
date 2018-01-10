package com.moviedb.ui.list


import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.interactors.GetAllMoviesInteractor
import com.moviedb.domain.pagination.GetAllMoviesPagination
import com.moviedb.domain.pagination.GetAllMoviesPaginationImpl
import com.moviedb.domain.usecase.GetMovieListUseCase
import com.moviedb.domain.usecase.UseCase
import dagger.Module
import dagger.Provides

@Module
class MovieListModule {

    @Provides
    fun provideGetAllMoviesPagination(): GetAllMoviesPagination {
        return GetAllMoviesPaginationImpl()
    }

    @Provides
    fun provideGetAllMoviesInteractor(pagination: GetAllMoviesPagination): GetAllMoviesInteractor {
        return GetAllMoviesInteractor(pagination)
    }

    @Provides
    fun provideGetAllUseCase(useCase: GetMovieListUseCase): UseCase<GetAllMoviesInteractor, MovieListResponseData> {
        return useCase
    }

    @Provides
    fun provideViewModelFactory(
            getAllUseCase: UseCase<GetAllMoviesInteractor, MovieListResponseData>,
            interactor: GetAllMoviesInteractor
    ): MovieListViewModelFactory {
        return MovieListViewModelFactory(getAllUseCase, interactor)
    }

}