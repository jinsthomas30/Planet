package com.example.planet.ui.planetlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.common.Constants.Companion.API_RESULT_OK
import com.example.planet.respository.MyRepository
import com.example.planet.ui.planetlist.data.PlanetItem
import com.example.planet.ui.planetlist.data.PlanetItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetListViewModel @Inject constructor(private val repository: MyRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _planetList = MutableStateFlow(emptyList<PlanetItem>())
    val planetList: MutableStateFlow<List<PlanetItem>> get() = _planetList

    init {
        fetchPlanetList()
    }

    private fun fetchPlanetList() {

        viewModelScope.launch {
            try {
                repository.planetList().let {
                    _isLoading.value = false
                    if (it.code() == API_RESULT_OK) {
                        _planetList.value = it.body()!!
                    } else {
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }

    }
}