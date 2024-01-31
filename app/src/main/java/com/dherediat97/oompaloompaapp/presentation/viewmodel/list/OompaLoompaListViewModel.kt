package com.dherediat97.oompaloompaapp.presentation.viewmodel.list

import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dherediat97.oompaloompaapp.domain.dto.OompaLoompa
import com.dherediat97.oompaloompaapp.domain.repository.OompaLoompaListRepository
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
        _oompaLoompaUiState.update {
            it.copy(
                isLoading = true
            )
        }
        runCatching {
            val responseGetAllOompaLoompa =
                repository.fetchAllOompaLoompa(page = oompaLoompaUiState.value.page)

            if (responseGetAllOompaLoompa.results.isNotEmpty()) {
                _oompaLoompaUiState.update {
                    it.copy(
                        page = oompaLoompaUiState.value.page,
                        oompaLoompaList = oompaLoompaUiState.value.oompaLoompaList + responseGetAllOompaLoompa.results
                    )
                }
                oompaLoompaUiState.value.page++
            }
        }.onFailure { error ->
            e("fetchAllWorkers", "onFailure", error)
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaList = mutableListOf(),
                    hasError = true,
                    isLoading = false
                )
            }
        }
        _oompaLoompaUiState.update {
            it.copy(
                isLoading = false
            )
        }
    }

    /**
     * Filter by profession if not have result, return original list
     */
    fun filterByProfession(professionSearched: String) = viewModelScope.launch(Dispatchers.IO) {
        _oompaLoompaUiState.update {
            it.copy(
                isLoading = true
            )
        }

        runCatching {
            val oompaLoompaList = _oompaLoompaUiState.value.oompaLoompaList

            //Filter using filter fun of profession parameter
            val oompaLoompaListFiltered = oompaLoompaList.filter {
                it.profession.contains(
                    professionSearched,
                    true
                )
            }
            //Update State
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaListFiltered = oompaLoompaListFiltered
                        .ifEmpty { oompaLoompaList }.distinct(),
                    isLoading = false
                )
            }
        }.onFailure {
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaListFiltered = mutableListOf(),
                    hasError = true,
                    isLoading = false
                )
            }
        }
    }

    /**
     * Filter by gender if any of two possible value(m or f)
     * have result, return original list
     */
    fun filterByGender(genderSearched: String) = viewModelScope.launch(Dispatchers.IO) {
        _oompaLoompaUiState.update {
            it.copy(
                isLoading = true
            )
        }
        runCatching {
            val oompaLoompaList = _oompaLoompaUiState.value.oompaLoompaList

            //Filter using filter fun of gender enum value(female or male) parameter
            val oompaLoompaListFiltered = oompaLoompaList.filter {
                genderSearched.lowercase().startsWith(it.gender.value.lowercase())
            }
            //Update State
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaListFiltered = oompaLoompaListFiltered
                        .ifEmpty { oompaLoompaList }.distinct(),
                    isLoading = false
                )
            }
        }.onFailure {
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaListFiltered = mutableListOf(),
                    hasError = true,
                    isLoading = false
                )
            }
        }
    }

    fun filterByGenderAndProfession(query: String) =
        viewModelScope.launch(Dispatchers.IO) {
            _oompaLoompaUiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val oompaLoompaList = _oompaLoompaUiState.value.oompaLoompaList
            val genderSearched = query.trim().split(":g:")
            val professionSearched = query.trim().split(":p:")
            println("gender= $genderSearched")
            println("profession = $professionSearched")

            if (genderSearched.isNotEmpty() && professionSearched.isNotEmpty()) {
                //Filter using filter fun of gender enum value(female or male)
                val oompaLoompaListFilteredByGender = oompaLoompaList.filter {
                    it.gender.value.lowercase() == genderSearched[1].lowercase()
                }

                //Filter using filter fun of profession value
                val oompaLoompaListFilteredByProfession = oompaLoompaList.filter {
                    it.profession.contains(
                        professionSearched[1],
                        true
                    )
                }

                //Update State
                _oompaLoompaUiState.update {
                    it.copy(
                        oompaLoompaListFiltered = oompaLoompaListFilteredByGender + oompaLoompaListFilteredByProfession.distinct(),
                        isLoading = false
                    )
                }

            }
        }


    /**
     * Filter by name, filter by first name or last name if any of two params
     * have result, return original list
     */
    fun filterByName(nameSearched: String) = viewModelScope.launch(Dispatchers.IO) {
        _oompaLoompaUiState.update {
            it.copy(
                isLoading = true
            )
        }

        runCatching {
            val oompaLoompaList = _oompaLoompaUiState.value.oompaLoompaList

            //Filter using filter fun of first name parameter
            val oompaLoompaListFilteredByName = oompaLoompaList.filter {
                it.firstName.contains(nameSearched, true)
            }

            //Filter using filter fun of last name parameter
            val oompaLoompaListFilteredByLastName = oompaLoompaList.filter {
                it.lastName.contains(nameSearched, true)
            }

            //Update State
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaListFiltered = oompaLoompaListFilteredByName + oompaLoompaListFilteredByLastName,
                    isLoading = false
                )
            }
        }.onFailure {
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaListFiltered = mutableListOf(),
                    hasError = true,
                    isLoading = false
                )
            }
        }


    }

    private fun resetOompaLoompaUiState() {
        _oompaLoompaUiState.update {
            it.copy(
                oompaLoompaList = mutableListOf(),
                oompaLoompaListFiltered = mutableListOf(),
                isLoading = false,
                hasError = false
            )
        }
    }


    /**
     * Main UiState that contains list array and control variables
     */
    data class OompaLoompaUiState(
        val oompaLoompaList: List<OompaLoompa> = mutableListOf(),
        val oompaLoompaListFiltered: List<OompaLoompa> = mutableListOf(),
        var page: Int = 1,
        val isLoading: Boolean = false,
        val hasError: Boolean = false
    )
}