package com.dherediat97.oompaloompaapp.domain.repository

import com.dherediat97.oompaloompaapp.domain.dto.OompaLoompa
import com.dherediat97.oompaloompaapp.data.network.OompaLoompaService


/**
 * Main Interface for OompaLoompa Detail feature
 */
interface OompaLoompaDetailRepository {
    suspend fun fetchSingleOompaLoompa(id: Int): OompaLoompa?
}


/**
 * The Implementation of the repository
 */
class OompaLoompaDetailRepositoryImpl(private val oompaLoompaService: OompaLoompaService) :
    OompaLoompaDetailRepository {

    /**
     * Fetch using the API REST and return to the View Model
     */
    override suspend fun fetchSingleOompaLoompa(id: Int): OompaLoompa? {
        return oompaLoompaService.fetchSingleOompaLoompa(id)
    }
}