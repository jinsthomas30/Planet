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
import com.example.planet.ui.planetdetails.data.PlanetDtEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("SENSELESS_COMPARISON")
@HiltViewModel
class PlanetDetailsViewModel @Inject constructor(
    private val repository: MyApiRepository,
    private val planetDbRepository: PlanetDbRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _isLoading = MutableStateFlow(LoaderState(isLoader = true))
    val isLoading = _isLoading.asStateFlow()

    private val _dialogState = MutableStateFlow<DialogState>(DialogState.Hidden)
    val dialogState: StateFlow<DialogState> get() = _dialogState

    private val _planetDetails = MutableStateFlow<PlanetDtEntity?>(null)
    val planetDetails: MutableStateFlow<PlanetDtEntity?> get() = _planetDetails

    fun fetchPlanetDt(uid: String) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                repository.planetDetails(uid).let { it ->
                    if (it.code() == Constants.API_RESULT_OK) {
                        if (it.body()?.message == Constants.MESSAGE_OK) {
                            val planetDt = it.body()?.result?.properties!!.also { it.uid = uid }
                            planetDbRepository.insertPlanetDt(planetDt)
                            getPlanetDtFromDb(uid)
                        }
                    }
                }
            } catch (e: Exception) {
                dismissLoader()
            }
        }

    }

    fun getPlanetDtFromDb(uid: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val data = planetDbRepository.getPlanetDt(uid)
            dismissLoader()
            if (data != null) {
                _planetDetails.value = data
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

    fun dismissLoader() {
        _isLoading.update { state ->
            state.copy(isLoader = false)
        }
    }

}