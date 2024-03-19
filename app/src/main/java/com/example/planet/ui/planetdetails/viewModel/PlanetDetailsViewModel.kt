package com.example.planet.ui.planetdetails.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.R
import com.example.planet.common.Constants
import com.example.planet.state.DialogState
import com.example.planet.state.LoaderState
import com.example.planet.resource.ResourceProvider
import com.example.planet.respository.MyApiRepository
import com.example.planet.respository.PlanetDbRepository
import com.example.planet.ui.planetdetails.data.PlanetDetailsResponse
import com.example.planet.ui.planetdetails.data.PlanetDtEntity
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

@Suppress("SENSELESS_COMPARISON")
@HiltViewModel
class PlanetDetailsViewModel @Inject constructor(
    private val myApiRepo: MyApiRepository,
    private val planetDbRepository: PlanetDbRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    // State flow for tracking loading state
    private val _isLoading = MutableStateFlow(LoaderState(isLoader = true))
    val isLoading: StateFlow<LoaderState> get() = _isLoading.asStateFlow()

    // State flow for managing dialog state
    private val _dialogState = MutableStateFlow<DialogState>(DialogState.Hidden)
    val dialogState: StateFlow<DialogState> get() = _dialogState

    // State flow for holding planet details
    private val _planetDetails = MutableStateFlow<PlanetDtEntity?>(null)
    val planetDetails: StateFlow<PlanetDtEntity?> get() = _planetDetails

    // Function to fetch planet details by UID
    fun fetchPlanetDetails(uid: String) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                // Checking if cache is available
                val cachedData = planetDbRepository.getPlanetDetailsByUid(uid)
                if (cachedData != null) {
                    dismissLoader()
                    _planetDetails.emit(cachedData)
                } else {
                    val planetDetailsResponse = myApiRepo.fetchPlanetDetails(uid)
                    handleApiResponse(planetDetailsResponse, uid)
                }
            } catch (e: IOException) {
                handleError(resourceProvider.getString(R.string.network_error))
            } catch (e: Exception) {
                handleError(resourceProvider.getString(R.string.unknown_error))
            }
        }
    }

    // Function to handle API response for planet details
    private fun handleApiResponse(response: Response<PlanetDetailsResponse>?, uid: String) {
        if (response?.isSuccessful == true) {
            val responseBody = response.body()
            if (responseBody != null && responseBody.message == Constants.MESSAGE_OK) {
                responseBody.result?.let { planetDetails ->
                    val planetDt = planetDetails.properties.also { it?.uid = uid }
                    viewModelScope.launch {
                        _planetDetails.emit(planetDt)
                        planetDt?.let { it1 -> planetDbRepository.insertPlanetDetailsByUid(it1) }
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
