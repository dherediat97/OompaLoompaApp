package com.dherediat97.oompaloompaapp.data.dto

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}