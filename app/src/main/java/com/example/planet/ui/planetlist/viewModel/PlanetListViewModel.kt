package com.example.planet.ui.planetlist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.R
import com.example.planet.common.Constants.Companion.MESSAGE_OK
import com.example.planet.resource.ResourceProvider
import com.example.planet.respository.MyApiRepository
import com.example.planet.respository.PlanetDbRepository
import com.example.planet.state.DialogState
import com.example.planet.state.LoaderState
import com.example.planet.ui.planetlist.data.PlanetEntity
import com.example.planet.ui.planetlist.data.PlanetResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PlanetListViewModel @Inject constructor(
    private val myApiRepo: MyApiRepository,
    private val planetDbRepository: PlanetDbRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    // State flow for tracking loading state
    private val _isLoading = MutableStateFlow(LoaderState(isLoader = true))
    val isLoading = _isLoading.asStateFlow()

    // State flow for holding planet list
    private val _planetList = MutableStateFlow(emptyList<PlanetEntity>())
    val planetList: MutableStateFlow<List<PlanetEntity>> get() = _planetList

    // State flow for managing dialog state
    private val _dialogState = MutableStateFlow<DialogState>(DialogState.Hidden)
    val dialogState: StateFlow<DialogState> get() = _dialogState

    // Function to fetch planet list
    fun fetchPlanetList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Checking if cache is available
                val data = planetDbRepository.getAllPlanet()
                if (data.isNotEmpty()) {
                    dismissLoader()
                    _planetList.emit(data)
                } else {
                    val planetListResponse = myApiRepo.fetchPlanetList()
                    handleApiResponse(planetListResponse)
                }
            } catch (e: IOException) {
                handleError(resourceProvider.getString(R.string.network_error))
            } catch (e: Exception) {
                handleError(resourceProvider.getString(R.string.unknown_error))
            }
        }
    }

    // Function to handle API response
    private fun handleApiResponse(response: Response<PlanetResponse>?) {
        if (response?.isSuccessful == true) {
            val responseBody = response.body()
            if (responseBody != null && responseBody.message == MESSAGE_OK) {
                responseBody.results.let { planets ->
                    viewModelScope.launch {
                        _planetList.emit(planets)
                        planetDbRepository.insertPlanetList(planets)
                    }
                    dismissLoader()
                    return
                }
            }
        }
        handleError(resourceProvider.getString(R.string.api_error))
    }

    // Function to show dialog
    private fun showDialog(message: String, buttonText: String) {
        _dialogState.value = DialogState.Show(message, buttonText)
    }

    // Function to dismiss dialog
    fun dismissDialog() {
        _dialogState.value = DialogState.Hidden
    }

    // Function to dismiss loader
    private fun dismissLoader() {
        _isLoading.update { state ->
            state.copy(isLoader = false)
        }
    }

    // Function to handle error
    private fun handleError(errorMessage: String) {
        dismissLoader()
        showDialog(errorMessage, resourceProvider.getString(R.string.ok))
    }

}