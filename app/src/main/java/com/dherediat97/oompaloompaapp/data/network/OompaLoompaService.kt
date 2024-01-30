package com.dherediat97.oompaloompaapp.data.network

import com.dherediat97.oompaloompaapp.domain.dto.OompaLoompa
import com.dherediat97.oompaloompaapp.domain.dto.ResponseGetAllOompaLoompa
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OompaLoompaService {
    @GET("oompa-loompas/")
    suspend fun fetchPaginatedOompaLoompas(@Query("page") page: Int): ResponseGetAllOompaLoompa

    @GET("oompa-loompas/{id}")
    suspend fun fetchSingleOompaLoompa(@Path("id") id: Int): OompaLoompa?
}