package com.dherediat97.oompaloompaapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dherediat97.oompaloompaapp.data.dto.OompaLoompa
import com.dherediat97.oompaloompaapp.repository.OompaLoompaDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OompaLoompaDetailViewModel(private val repository: OompaLoompaDetailRepository) :
    ViewModel() {

    private val _singleOompaLoompaUiState = MutableStateFlow(SingleOompaLoompaUiState())
    val singleOompaLoompaUiState: StateFlow<SingleOompaLoompaUiState> = _singleOompaLoompaUiState.asStateFlow()

    /**
     * reset ui state
     */
    init {
        resetOompaLoompaUiState()
    }


    /**
     * Fetch single Oompa loompa controlling the errors
     * and return the response to the composable view
     */
    fun fetchSingleOompaLoompa(oompaLoompaId: Int) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            _singleOompaLoompaUiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val responseGetSingleOompaLoompa = repository.fetchSingleOompaLoompa(oompaLoompaId)
            if (responseGetSingleOompaLoompa != null) {
                _singleOompaLoompaUiState.update {
                    it.copy(
                        oompaLoompaWorker = responseGetSingleOompaLoompa,
                        isLoading = false
                    )
                }
            }
        }.onFailure { error ->
            Log.e("fetchAllWorkers", "onFailure", error)
            _singleOompaLoompaUiState.update {
                it.copy(
                    oompaLoompaWorker = null,
                    error = "Se ha encontrado un error"
                )
            }
        }
        _singleOompaLoompaUiState.update {
            it.copy(
                isLoading = false
            )
        }
    }


    private fun resetOompaLoompaUiState() {
        _singleOompaLoompaUiState.update {
            it.copy(
                oompaLoompaWorker = null,
                isLoading = false,
                error = ""
            )
        }
    }


    /**
     * Main UiState that contains Oompa Loompa object and control variables
     */
    data class SingleOompaLoompaUiState(
        val oompaLoompaWorker: OompaLoompa? = null,
        val isLoading: Boolean = false,
        val error: String = ""
    )
}