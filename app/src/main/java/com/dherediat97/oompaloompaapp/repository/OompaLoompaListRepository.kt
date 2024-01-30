package com.dherediat97.oompaloompaapp.repository

import com.dherediat97.oompaloompaapp.data.dto.ResponseGetAllOompaLoompa
import com.dherediat97.oompaloompaapp.service.OompaLoompaService


/**
 * Main Interface for OompaLoompa List feature
 */
interface OompaLoompaListRepository {
    suspend fun fetchAllOompaLoompa(): ResponseGetAllOompaLoompa
}


/**
 * The Implementation of the repository
 */
class OompaLoompaListRepositoryImpl(private val oompaLoompaService: OompaLoompaService) :
    OompaLoompaListRepository {

    /**
     * Fetch using the API REST and return to the View Model
     */
    override suspend fun fetchAllOompaLoompa(): ResponseGetAllOompaLoompa {
        return oompaLoompaService.fetchPaginatedOompaLoompas()
    }
}