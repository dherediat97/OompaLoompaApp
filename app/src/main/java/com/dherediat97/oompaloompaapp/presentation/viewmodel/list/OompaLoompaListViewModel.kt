package com.dherediat97.oompaloompaapp.presentation.viewmodel.list

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
    private var filterByName: Boolean = true
    private var filterByBoth: Boolean = false
    private val _termSearched = MutableStateFlow("")
    var termSearched = _termSearched.asStateFlow()

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
                repository.fetchAllOompaLoompa(page = _oompaLoompaUiState.value.page)

            if (responseGetAllOompaLoompa.results.isNotEmpty()) {
                var oompaLoompaList = responseGetAllOompaLoompa.results

                if (filterByName) oompaLoompaList = oompaLoompaList.filterByName(termSearched.value)
                else if (filterByGender) oompaLoompaList =
                    oompaLoompaList.filterByGender(termSearched.value)
                else if (filterByProfession) oompaLoompaList =
                    oompaLoompaList.filterByProfession(termSearched.value)
                else if (filterByBoth) oompaLoompaList =
                    oompaLoompaList.filterByProfessionAndGender(termSearched.value)

                _oompaLoompaUiState.update {
                    it.copy(
                        page = _oompaLoompaUiState.value.page,
                        oompaLoompaList = _oompaLoompaUiState.value.oompaLoompaList + oompaLoompaList
                    )
                }
                _oompaLoompaUiState.value.page++
            }

        }.onFailure {
            _oompaLoompaUiState.update {
                it.copy(
                    hasError = true
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
    fun filterByProfession() = viewModelScope.launch(Dispatchers.IO) {
        filterByProfession = true
        filterByName = false
        filterByGender = false
        filterByBoth = false

        _oompaLoompaUiState.update {
            it.copy(
                isLoading = true
            )
        }

        runCatching {
            //Update State
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaList = _oompaLoompaUiState.value.oompaLoompaList.filterByProfession(
                        termSearched.value
                    ),
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
    fun filterByGender() = viewModelScope.launch(Dispatchers.IO) {
        filterByGender = true
        filterByName = false
        filterByProfession = false
        filterByBoth = false

        _oompaLoompaUiState.update {
            it.copy(
                isLoading = true
            )
        }
        runCatching {
            //Update State
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaList = _oompaLoompaUiState.value.oompaLoompaList.filterByGender(
                        termSearched.value.lowercase()
                    ),
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

    fun filterByGenderAndProfession() = viewModelScope.launch(Dispatchers.IO) {
        filterByBoth = true
        filterByGender = false
        filterByName = false
        filterByProfession = false

        _oompaLoompaUiState.update {
            it.copy(
                isLoading = true
            )
        }
        runCatching {
            if (termSearched.value.isNotEmpty()) {
                //Filter using filter fun of profession value and
                //filter using filter fun of gender enum value(female or male)
                val oompaLoompaListFilteredByGenderAndProfession =
                    _oompaLoompaUiState.value.oompaLoompaList.filterByProfessionAndGender(
                        termSearched.value
                    )

                println(oompaLoompaListFilteredByGenderAndProfession)

                //Update State
                _oompaLoompaUiState.update {
                    it.copy(
                        oompaLoompaList = oompaLoompaListFilteredByGenderAndProfession
                    )
                }
            }


        }.onFailure {
            _oompaLoompaUiState.update {
                it.copy(
                    hasError = true
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
     * Filter by name, filter by first name or last name if any of two params
     * have result, return original list
     */
    fun filterByName() = viewModelScope.launch(Dispatchers.IO) {
        filterByName = true
        filterByProfession = false
        filterByGender = false
        filterByBoth = false

        _oompaLoompaUiState.update {
            it.copy(
                isLoading = true
            )
        }

        runCatching {
            //Update State
            _oompaLoompaUiState.update {
                it.copy(
                    oompaLoompaList = (_oompaLoompaUiState.value.oompaLoompaList.filterByName(
                        termSearched.value
                    )),
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

    fun onSearchTextChange(text: String) {
        _termSearched.value = text
    }

    fun resetOompaLoompaUiState() = viewModelScope.launch(Dispatchers.IO) {
        filterByProfession = false
        filterByName = true
        filterByGender = false
        filterByBoth = false
        fetchAllWorkers()
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


    /**
     * Extensions
     *
     */
    private fun List<OompaLoompa>.filterByProfession(profession: String): List<OompaLoompa> {
        return filter {
            it.profession.contains(
                profession,
                true
            )
        }.distinct()
    }

    private fun List<OompaLoompa>.filterByName(name: String): List<OompaLoompa> {
        return filter {
            it.firstName.contains(
                name,
                true
            )
        }.distinct() + filter {
            it.lastName.contains(
                name,
                true
            )
        }.distinct()
    }

    private fun List<OompaLoompa>.filterByGender(name: String): List<OompaLoompa> {
        return when (name) {
            "female" -> {
                filter {
                    it.gender.name == "F"
                }.distinct()
            }

            "male" -> {
                return filter {
                    it.gender.name == "M"
                }.distinct()
            }

            else -> listOf()
        }
    }


    /**
     * Multiple Filter(Proffesion and Gender)
     */
    private fun List<OompaLoompa>.filterByProfessionAndGender(termSearched: String): List<OompaLoompa> {
        val termSearchedSplit = termSearched.split(" ")
        return if (termSearchedSplit.size > 2) {
            return filter {
                (it.profession == termSearchedSplit[0]) && (it.gender.value.lowercase() == termSearchedSplit[1])
            }.distinct()
        } else listOf()
    }
}