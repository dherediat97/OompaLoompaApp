package com.dherediat97.oompaloompaapp.service

import com.dherediat97.oompaloompaapp.data.dto.ResponseGetAllOompaLoompa
import retrofit2.http.GET

interface OompaLoompaService {
    @GET("oompa-loompas/")
    suspend fun fetchAllWorkers(): ResponseGetAllOompaLoompa
}