package com.mfarhan08a.simplelyricsapp.di

import com.mfarhan08a.simplelyricsapp.core.domain.usecase.TrackInteractor
import com.mfarhan08a.simplelyricsapp.core.domain.usecase.TrackUseCase
import com.mfarhan08a.simplelyricsapp.ui.detail.DetailViewModel
import com.mfarhan08a.simplelyricsapp.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<TrackUseCase> { TrackInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}