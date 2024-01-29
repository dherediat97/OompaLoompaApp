package com.dherediat97.oompaloompaapp.presentation.viewmodel

import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dherediat97.oompaloompaapp.repository.OompaLoompaListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * [OompaLoompaListViewModel] return all Oompa Loompa to the list composable
 */
class OompaLoompaListViewModel(private val repository: OompaLoompaListRepository) : ViewModel() {

    fun fetchAllWorkers() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            repository.fetchAllOompaLoompa()
        }.onFailure { error ->
            e("fetchAllWorkers", "onFailure", error)
        }
    }
}