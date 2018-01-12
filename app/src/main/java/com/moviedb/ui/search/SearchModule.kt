package com.moviedb.ui.search

import com.moviedb.domain.interactors.SearchMoviesInteractor
import com.moviedb.domain.model.MovieListResponseData
import com.moviedb.domain.interactors.SearchMoviesInteractorData
import com.moviedb.domain.schedulers.RxSchedulers
import com.moviedb.domain.usecase.SearchMoviesUseCase
import com.moviedb.domain.usecase.UseCase
import com.moviedb.domain.pagination.SearchPagination
import com.moviedb.domain.pagination.SearchPaginationImpl
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @Provides
    fun provideSearchPagination(): SearchPagination {
        return SearchPaginationImpl()
    }

    @Provides
    fun provideSearchMoviesInteractor(pagination: SearchPagination): SearchMoviesInteractor {
        return SearchMoviesInteractorData(pagination, null)
    }

    @Provides
    fun provideSearchUseCase(useCase: SearchMoviesUseCase): UseCase<SearchMoviesInteractor, MovieListResponseData> {
        return useCase
    }

    @Provides
    fun provideRxQuery(schedulers: RxSchedulers): RxQuery {
        return RxQueryImpl(schedulers)
    }

    @Provides
    fun provideViewModelFactory(
            searchUseCase: UseCase<SearchMoviesInteractor, MovieListResponseData>,
            interactor: SearchMoviesInteractor,
            rxQuery: RxQuery
    ): SearchViewModelFactory {
        return SearchViewModelFactory(searchUseCase, interactor , rxQuery)
    }

}