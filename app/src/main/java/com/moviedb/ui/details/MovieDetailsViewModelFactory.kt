package com.moviedb.ui.details

import com.moviedb.data.model.MovieDetailData
import com.moviedb.domain.interactors.GetMovieInteractor
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.base.BaseViewModelFactory
import javax.inject.Inject


class MovieDetailsViewModelFactory
@Inject constructor(
        private val getDetailsUseCase: UseCase<GetMovieInteractor, MovieDetailData>
) : BaseViewModelFactory<MovieDetailsViewModel>() {

    override val viewModel: MovieDetailsViewModel by lazy {
        MovieDetailsViewModel(getDetailsUseCase)
    }

    override val vmClass: Class<MovieDetailsViewModel> = MovieDetailsViewModel::class.java
}