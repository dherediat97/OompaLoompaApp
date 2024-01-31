package com.dherediat97.oompaloompaapp.domain.dto

data class ResponseGetAllOompaLoompa(
    val current: Int,
    val total: Int,
    val results: List<OompaLoompa>
)