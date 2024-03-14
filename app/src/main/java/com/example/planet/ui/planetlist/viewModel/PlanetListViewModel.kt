package com.example.planet.ui.planetlist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.R
import com.example.planet.common.Constants.Companion.API_RESULT_OK
import com.example.planet.common.Constants.Companion.MESSAGE_OK
import com.example.planet.resource.ResourceProvider
import com.example.planet.respository.MyApiRepository
import com.example.planet.respository.PlanetDbRepository
import com.example.planet.state.DialogState
import com.example.planet.state.LoaderState
import com.example.planet.ui.planetlist.data.PlanetEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetListViewModel @Inject constructor(
    private val repository: MyApiRepository,
    private val planetDbRepository: PlanetDbRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _isLoading = MutableStateFlow(LoaderState(isLoader = true))
    val isLoading = _isLoading.asStateFlow()

    private val _planetList = MutableStateFlow(emptyList<PlanetEntity>())
    val planetList: MutableStateFlow<List<PlanetEntity>> get() = _planetList

    private val _dialogState = MutableStateFlow<DialogState>(DialogState.Hidden)
    val dialogState: StateFlow<DialogState> get() = _dialogState

    fun fetchPlanetList() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val job = repository.planetList().let {
                    if (it.code() == API_RESULT_OK) {
                        if (it.body()?.message == MESSAGE_OK) {
                            it.body()?.results?.let { it1 -> planetDbRepository.insertPlanetList(it1) }
                            fetchDataFromDb()
                        }
                    }
                }
            } catch (e: Exception) {
                dismissLoader()
            }
        }
    }

    fun fetchDataFromDb() {
        viewModelScope.launch(Dispatchers.Default) {
            val data = planetDbRepository.getAllPlanet()
            dismissLoader()
            if (data.isNotEmpty()) {
                _planetList.value = data
            } else {
                showDialog(
                    resourceProvider.getString(R.string.alert_msg),
                    resourceProvider.getString(R.string.ok)
                )
            }

        }
    }

    private fun showDialog(message: String, buttonText: String) {
        _dialogState.value = DialogState.Show(message, buttonText)
    }

    fun dismissDialog() {
        _dialogState.value = DialogState.Hidden
    }

    private fun dismissLoader() {
        _isLoading.update { state ->
            state.copy(isLoader = false)
        }
    }

}