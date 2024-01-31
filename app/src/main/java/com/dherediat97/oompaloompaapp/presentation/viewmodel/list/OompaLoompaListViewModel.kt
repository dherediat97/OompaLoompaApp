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

    private var filterByGender: Boolean = false
    private var filterByProfession: Boolean = false
    private var filterByName: Boolean = false
    private var filterByBoth: Boolean = false

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
    fun fetchAllWorkers(loadNextPage: Boolean = false) = viewModelScope.launch(Dispatchers.IO) {
        if (_oompaLoompaUiState.value.oompaLoompaList.isNotEmpty()) return@launch

        _oompaLoompaUiState.update {
            it.copy(
                isLoading = true
            )
        }
        runCatching {
            val responseGetAllOompaLoompa =
                repository.fetchAllOompaLoompa(page = _oompaLoompaUiState.value.page)

            if (responseGetAllOompaLoompa.results.isNotEmpty()) {
                val oompaLoompaList = responseGetAllOompaLoompa.results
                if (loadNextPage) {
                    _oompaLoompaUiState.update {
                        it.copy(
                            page = _oompaLoompaUiState.value.page,
                            oompaLoompaList = _oompaLoompaUiState.value.oompaLoompaList + oompaLoompaList
                        )
                    }
                    _oompaLoompaUiState.value.page++
                } else {
                    _oompaLoompaUiState.update {
                        it.copy(
                            page = 1,
                            oompaLoompaList = oompaLoompaList
                        )
                    }
                }
            }

        }.onFailure { error ->
            e("fetchAllWorkers", "onFailure", error)
            _oompaLoompaUiState.update {
                it.copy(
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
        filterByProfession = true
        _oompaLoompaUiState.update {
            it.copy(
                isLoading = true
            )
        }

        runCatching {
            //Filter using filter fun of profession parameter
            val oompaLoompaListFiltered = _oompaLoompaUiState.value.oompaLoompaList.filter {
                it.profession.contains(
                    professionSearched,
                    true
                )
            }.distinct()

            //Update State
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaList = oompaLoompaListFiltered.distinct(),
                    isLoading = false
                )
            }
        }.onFailure {
            _oompaLoompaUiState.update {
                it.copy(
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
        filterByGender = true

        if (_oompaLoompaUiState.value.oompaLoompaList.isNotEmpty()) fetchAllWorkers()

        _oompaLoompaUiState.update {
            it.copy(
                isLoading = true
            )
        }
        runCatching {
            var oompaLoompaListFiltered: List<OompaLoompa> = mutableListOf()
            val genderSearchedLower = genderSearched.lowercase()

            //Filter using filter fun of gender enum value(female or male) parameter
            if (genderSearchedLower == "female") {
                oompaLoompaListFiltered = _oompaLoompaUiState.value.oompaLoompaList.filter {
                    it.gender.name == "F"
                }.distinct()
            } else if (genderSearchedLower == "male") {
                oompaLoompaListFiltered = _oompaLoompaUiState.value.oompaLoompaList.filter {
                    it.gender.name == "M"
                }.distinct()
            }
            //Update State
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaList = oompaLoompaListFiltered.distinct(),
                    isLoading = false
                )
            }
        }.onFailure {
            _oompaLoompaUiState.update {
                it.copy(
                    hasError = true,
                    isLoading = false
                )
            }
        }
    }

    fun filterByGenderAndProfession(query: String) =
        viewModelScope.launch(Dispatchers.IO) {
            filterByBoth = true

            if (_oompaLoompaUiState.value.oompaLoompaList.isNotEmpty()) fetchAllWorkers()

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
                }.distinct()

                //Filter using filter fun of profession value
                val oompaLoompaListFilteredByProfession = oompaLoompaList.filter {
                    it.profession.contains(
                        professionSearched[1],
                        true
                    )
                }.distinct()

                //Update State
                _oompaLoompaUiState.update {
                    it.copy(
                        oompaLoompaList = oompaLoompaListFilteredByGender + oompaLoompaListFilteredByProfession,
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
        filterByName = true

        if (_oompaLoompaUiState.value.oompaLoompaList.isNotEmpty()) fetchAllWorkers()

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
            }.distinct()

            //Filter using filter fun of last name parameter
            val oompaLoompaListFilteredByLastName = oompaLoompaList.filter {
                it.lastName.contains(nameSearched, true)
            }.distinct()

            println(oompaLoompaListFilteredByName)
            println(oompaLoompaListFilteredByLastName)

            //Update State
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaList = (oompaLoompaListFilteredByName + oompaLoompaListFilteredByLastName),
                    isLoading = false
                )
            }
        }.onFailure {
            _oompaLoompaUiState.update {
                it.copy(
                    hasError = true,
                    isLoading = false
                )
            }
        }


    }

    fun resetOompaLoompaUiState() = viewModelScope.launch(Dispatchers.IO) {
        filterByProfession = false
        filterByName = false
        filterByGender = false
        filterByBoth = false

        _oompaLoompaUiState.update {
            it.copy(
                isLoading = true,
            )
        }
        runCatching {
            val responseGetAllOompaLoompa =
                repository.fetchAllOompaLoompa(page = _oompaLoompaUiState.value.page)

            if (responseGetAllOompaLoompa.results.isNotEmpty()) {
                val oompaLoompaList = responseGetAllOompaLoompa.results

                _oompaLoompaUiState.update {
                    it.copy(
                        oompaLoompaList = oompaLoompaList,
                        isLoading = false,
                        hasError = false
                    )
                }
            }
        }.onFailure {
            _oompaLoompaUiState.update {
                it.copy(
                    hasError = false
                )
            }
        }

    }


    /**
     * Main UiState that contains list array and control variables
     */
    data class OompaLoompaUiState(
        val oompaLoompaList: List<OompaLoompa> = mutableListOf(),
        var page: Int = 1,
        val isLoading: Boolean = false,
        val hasError: Boolean = false
    )
}