package com.example.planet.ui.planetdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.common.Constants
import com.example.planet.respository.MyApiRepository
import com.example.planet.respository.PlanetDbRepository
import com.example.planet.ui.planetdetails.data.Properties
import com.example.planet.ui.planetlist.PlanetListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetDetailsViewModel @Inject constructor(
    private val repository: MyApiRepository,
    private val planetDbRepository: PlanetDbRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _planetDetails =
        MutableStateFlow(Properties("", "", "", "", "", "", "", "", "", "", "", ""))
    val planetDetails: MutableStateFlow<Properties> get() = _planetDetails


    fun fetchPlanetList(uid: String) {
        viewModelScope.launch {
            try {
                repository.planetDetails(uid).let {
                    _isLoading.value = false
                    if (it.code() == Constants.API_RESULT_OK) {
                        if (it.body()?.message == Constants.MESSAGE_OK) {
                            //   planetDbRepository.insertTask(it.body()?.results!!)
                            _planetDetails.value= it.body()?.result?.properties!!

                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }

    }

}