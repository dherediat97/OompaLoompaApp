package com.dherediat97.oompaloompaapp.di

import com.dherediat97.oompaloompaapp.domain.repository.OompaLoompaDetailRepository
import com.dherediat97.oompaloompaapp.domain.repository.OompaLoompaDetailRepositoryImpl
import com.dherediat97.oompaloompaapp.domain.repository.OompaLoompaListRepository
import com.dherediat97.oompaloompaapp.domain.repository.OompaLoompaListRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<OompaLoompaListRepository> { OompaLoompaListRepositoryImpl(get()) }
    single<OompaLoompaDetailRepository> { OompaLoompaDetailRepositoryImpl(get()) }
}