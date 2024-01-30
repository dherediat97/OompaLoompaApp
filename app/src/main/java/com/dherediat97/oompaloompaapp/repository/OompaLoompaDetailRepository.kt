package com.dherediat97.oompaloompaapp.repository

import com.dherediat97.oompaloompaapp.data.dto.OompaLoompa
import com.dherediat97.oompaloompaapp.data.dto.ResponseGetAllOompaLoompa
import com.dherediat97.oompaloompaapp.service.OompaLoompaService


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