package com.dherediat97.oompaloompaapp.presentation.viewmodel

import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dherediat97.oompaloompaapp.data.dto.OompaLoompa
import com.dherediat97.oompaloompaapp.repository.OompaLoompaListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * [OompaLoompaListViewModel] return all Oompa Loompa to the list composable
 */
class OompaLoompaListViewModel(private val repository: OompaLoompaListRepository) : ViewModel() {

    private val _oompaLoompaUiState = MutableStateFlow(OompaLoompaUiState())
    val oompaLoompaUiState: StateFlow<OompaLoompaUiState> = _oompaLoompaUiState.asStateFlow()

    /**
     * reset ui state
     */
    init {
        resetOompaLoompaUiState()
    }


    /**
     * Fetch all Oompa loompa controlling the errors
     * and return the response to the composable view
     */
    fun fetchAllWorkers() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            _oompaLoompaUiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val responseGetAllOompaLoompa = repository.fetchAllOompaLoompa()
            if (responseGetAllOompaLoompa.results.isNotEmpty()) {
                _oompaLoompaUiState.update {
                    it.copy(
                        oompaLoompaList = responseGetAllOompaLoompa.results,
                        isLoading = false
                    )
                }
            }
        }.onFailure { error ->
            e("fetchAllWorkers", "onFailure", error)
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaList = mutableListOf(),
                    error = "Se ha encontrado un error"
                )
            }
        }
        _oompaLoompaUiState.update {
            it.copy(
                isLoading = false
            )
        }
    }


    private fun resetOompaLoompaUiState() {
        _oompaLoompaUiState.update {
            it.copy(
                oompaLoompaList = mutableListOf(),
                isLoading = false,
                error = ""
            )
        }
    }


    /**
     * Main UiState that contains list array and control variables
     */
    data class OompaLoompaUiState(
        val oompaLoompaList: List<OompaLoompa> = mutableListOf(),
        val isLoading: Boolean = false,
        val error: String = ""
    )
}