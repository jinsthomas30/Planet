package com.example.planet.ui.planetlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.common.Constants.Companion.API_RESULT_OK
import com.example.planet.common.Constants.Companion.MESSAGE_OK
import com.example.planet.respository.MyApiRepository
import com.example.planet.respository.PlanetDbRepository
import com.example.planet.ui.planetlist.data.PlanetEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetListViewModel @Inject constructor(private val repository: MyApiRepository,
                                              private val planetDbRepository: PlanetDbRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _planetList = MutableStateFlow(emptyList<PlanetEntity>())
    val planetList: MutableStateFlow<List<PlanetEntity>> get() = _planetList


    init {
        fetchPlanetList()
    }

    private fun fetchPlanetList() {
        viewModelScope.launch {
            try {
                repository.planetList().let {
                    _isLoading.value = false
                    if (it.code() == API_RESULT_OK) {
                        if(it.body()?.message == MESSAGE_OK){
                            planetDbRepository.insertTask(it.body()?.results!!)
                            fetchDataFromDb()
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                fetchDataFromDb()
            }
        }

    }

    private fun fetchDataFromDb() {
        viewModelScope.launch {
            _planetList.value = planetDbRepository.getAllTask()
        }
    }
}