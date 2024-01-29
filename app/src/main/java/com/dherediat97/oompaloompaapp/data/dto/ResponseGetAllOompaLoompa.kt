package com.dherediat97.oompaloompaapp.data.dto

data class ResponseGetAllOompaLoompa(
    val current: Int,
    val total: Int,
    val results: List<OompaLoompa>
)