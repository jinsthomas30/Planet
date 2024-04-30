package com.example.planet.planetlist.prensentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.R
import com.example.planet.utils.Constants.Companion.MESSAGE_OK
import com.example.planet.utils.ResourceProvider
import com.example.planet.common.data.DialogState
import com.example.planet.common.data.LoaderState
import com.example.planet.planetlist.data.PlanetEntity
import com.example.planet.planetlist.data.PlanetResponse
import com.example.planet.planetlist.domain.usecase.GetPlanetListLocalUseCase
import com.example.planet.planetlist.domain.usecase.GetPlanetListRemoteUsecase
import com.example.planet.planetlist.domain.usecase.InsertPlanetListUseCase
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
    val getPlanetListRemoteUsecase: GetPlanetListRemoteUsecase,
    val insertPlanetListUseCase: InsertPlanetListUseCase,
    val getPlanetListLocalUseCase: GetPlanetListLocalUseCase,
    val resourceProvider: ResourceProvider
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
                val data = getPlanetListLocalUseCase(GetPlanetListLocalUseCase.Param(""))
                if (data.isNotEmpty()) {
                    dismissLoader()
                    _planetList.emit(data)
                } else {
                    val planetListResponse = getPlanetListRemoteUsecase(GetPlanetListRemoteUsecase.Param(""))
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
            responseBody.let {
                if (responseBody?.message == MESSAGE_OK) {
                    responseBody.results.let { planets ->
                        viewModelScope.launch {
                            _planetList.emit(planets)
                            insertPlanetListUseCase(InsertPlanetListUseCase.Param(planets))
                        }
                        dismissLoader()
                        return
                    }
                }else{
                    handleError(resourceProvider.getString(R.string.api_error))
                }
            }
        }
        handleError(resourceProvider.getString(R.string.api_error))
    }

    // Function to show dialog
    fun showDialog(message: String, buttonText: String) {
        _dialogState.value = DialogState.Show(message, buttonText)
    }

    // Function to dismiss dialog
    fun dismissDialog() {
        _dialogState.value = DialogState.Hidden
    }

    // Function to dismiss loader
    fun dismissLoader() {
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