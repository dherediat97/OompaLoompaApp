package com.dherediat97.oompaloompaapp.domain.repository

import com.dherediat97.oompaloompaapp.domain.dto.ResponseGetAllOompaLoompa
import com.dherediat97.oompaloompaapp.data.network.OompaLoompaService


/**
 * Main Interface for OompaLoompa List feature
 */
interface OompaLoompaListRepository {
    suspend fun fetchAllOompaLoompa(page: Int): ResponseGetAllOompaLoompa
}


/**
 * The Implementation of the repository
 */
class OompaLoompaListRepositoryImpl(private val oompaLoompaService: OompaLoompaService) :
    OompaLoompaListRepository {

    /**
     * Fetch using the API REST and return to the View Model
     */
    override suspend fun fetchAllOompaLoompa(page: Int): ResponseGetAllOompaLoompa {
        return oompaLoompaService.fetchPaginatedOompaLoompas(page)
    }
}