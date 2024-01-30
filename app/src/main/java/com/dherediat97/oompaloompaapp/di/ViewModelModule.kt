package com.dherediat97.oompaloompaapp.di

import com.dherediat97.oompaloompaapp.presentation.viewmodel.detail.OompaLoompaDetailViewModel
import com.dherediat97.oompaloompaapp.presentation.viewmodel.list.OompaLoompaListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * View Models Module
 */
val viewModelModule = module {
    viewModel { OompaLoompaListViewModel(get()) }
    viewModel { OompaLoompaDetailViewModel(get()) }
}