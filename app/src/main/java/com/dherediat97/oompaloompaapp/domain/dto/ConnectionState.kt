package com.dherediat97.oompaloompaapp.domain.dto

sealed class ConnectionState {
    data object Available : ConnectionState()
    data object Unavailable : ConnectionState()
}