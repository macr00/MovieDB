package com.moviedb.domain.usecase

import com.moviedb.*
import com.moviedb.domain.MovieRepository
import com.moviedb.domain.interactors.SearchMoviesInteractor
import com.moviedb.domain.model.MovieListResponseData
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Test
import org.mockito.Mockito.*


class SearchMoviesUseCaseTest {

    @Test
    fun whenRepoEmitsDataThenResultsObservedAndInteractorUpdated() {
        SearchMoviesUseCaseScenario().run {
            given {
                interactorHasNext()
                repositoryReturnsData()
            }
            whenever {
                executeUseCase()
            }
            then {
                resultsObserved()
                interactorUpdated()
            }
        }
    }

    @Test
    fun whenInteractorDoesNotHaveNextThenResultIsEmpty() {
        SearchMoviesUseCaseScenario().run {
            given {
                interactorDoesNotHaveNext()
            }
            whenever {
                executeUseCase()
            }
            then {
                emptyResults()
                interactorNotUpdated()
            }
        }
    }

    @Test
    fun whenRepoEmitsErrorThenInteractorNotUpdated() {
        SearchMoviesUseCaseScenario().run {
            given {
                interactorHasNext()
                repositoryError()
            }
            whenever {
                executeUseCase()
            }
            then {
                errorObserved()
                interactorNotUpdated()
            }
        }
    }

}

class SearchMoviesUseCaseScenario {

    companion object {
        const val QUERY = "query"
        const val YEAR = 2018
        const val NEXT_PAGE= 2
    }

    private val repository = mock(MovieRepository::class.java)
    private val interactor = mock(SearchMoviesInteractor::class.java)
    private val schedulers = RxSchedulersTrampoline()
    private val subscriber = TestSubscriber.create<MovieListResponseData>()
    private val response = MovieListResponseData(
            page = 1,
            totalPages = 3,
            totalResults = 55,
            results = emptyList()
    )
    private val error = Throwable("Mock Error from Repo")
    private val useCase = SearchMoviesUseCase(repository, schedulers)

    init {
        `when`(interactor.query).thenReturn(QUERY)
        `when`(interactor.year).thenReturn(YEAR)
        `when`(interactor.nextPage()).thenReturn(NEXT_PAGE)
    }

    fun given(func: Given.() -> Unit) = Given().apply(func)

    fun whenever(func: Whenever.() -> Unit) = Whenever().apply(func)

    fun then(func: Then.() -> Unit) = Then().apply(func)

    inner class Given {

        fun interactorHasNext() {
            `when`(interactor.hasNext()).thenReturn(true)
        }

        fun interactorDoesNotHaveNext() {
            `when`(interactor.hasNext()).thenReturn(false)
        }

        fun repositoryReturnsData() {
            `when`(repository.search(NEXT_PAGE, YEAR, QUERY))
                    .thenReturn(Flowable.just(response))
        }

        fun repositoryError() {
            `when`(repository.search(NEXT_PAGE, YEAR, QUERY))
                    .thenReturn(Flowable.error(error))
        }

    }

    inner class Whenever {

        fun executeUseCase() {
            useCase.execute(interactor).subscribe(subscriber)
        }
    }

    inner class Then {

        fun interactorUpdated() {
            verify(interactor).processResult(response)
        }

        fun interactorNotUpdated() {
            verify(interactor, never()).processResult(response)
        }

        fun resultsObserved() {
            subscriber.assertNoErrors()
            subscriber.assertValue(response)
        }

        fun emptyResults() {
            subscriber.assertNoErrors()
            subscriber.assertNoValues()
        }

        fun errorObserved() {
            subscriber.assertError(error)
        }
    }

}