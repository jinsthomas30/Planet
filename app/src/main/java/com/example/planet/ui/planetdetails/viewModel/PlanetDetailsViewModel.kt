package com.example.planet.ui.planetdetails.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.common.Constants
import com.example.planet.respository.MyApiRepository
import com.example.planet.respository.PlanetDbRepository
import com.example.planet.ui.planetdetails.data.MyDialog
import com.example.planet.ui.planetdetails.data.PlanetDtEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("SENSELESS_COMPARISON")
@HiltViewModel
class PlanetDetailsViewModel @Inject constructor(
    private val repository: MyApiRepository,
    private val planetDbRepository: PlanetDbRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _openDialog = MutableStateFlow(MyDialog(showDialog = false))
    val openDialog = _openDialog.asStateFlow()

    private val _planetDetails = MutableStateFlow<PlanetDtEntity?>(null)
    val planetDetails: MutableStateFlow<PlanetDtEntity?> get() = _planetDetails

    fun fetchPlanetDt(uid: String) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                repository.planetDetails(uid).let { it ->
                    _isLoading.value = false
                    if (it.code() == Constants.API_RESULT_OK) {
                        if (it.body()?.message == Constants.MESSAGE_OK) {
                            val planetDt= it.body()?.result?.properties!!.also { it.uid=uid }
                            planetDbRepository.insertPlanetDt(planetDt)
                            getPlanetDtFromDb(uid)
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                getPlanetDtFromDb(uid)
            }
        }

    }
    private fun getPlanetDtFromDb(uid: String) {
        viewModelScope.launch(Dispatchers.Default) {
           val data= planetDbRepository.getPlanetDt(uid)
            if(data!=null){
                _planetDetails.value =data
            }else {
                onShowDialog()
            }
        }
    }

    private fun onShowDialog() {
        _openDialog.update { state ->
            state.copy(showDialog = true)
        }
    }
    fun onDismiss() {
        _openDialog.update { state ->
            state.copy(showDialog = false)
        }
    }

}