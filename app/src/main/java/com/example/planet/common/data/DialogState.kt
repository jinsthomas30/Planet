package com.example.planet.common.data

sealed class DialogState {
    data object Hidden : DialogState()
    data class Show(val message: String, val buttonText: String) : DialogState()
}
