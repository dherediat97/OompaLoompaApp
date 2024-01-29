package com.dherediat97.oompaloompaapp.di

import com.dherediat97.oompaloompaapp.repository.OompaLoompaListRepository
import com.dherediat97.oompaloompaapp.repository.OompaLoompaListRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<OompaLoompaListRepository> { OompaLoompaListRepositoryImpl(get()) }
}