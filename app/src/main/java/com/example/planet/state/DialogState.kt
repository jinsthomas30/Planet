package com.example.planet.state

sealed class DialogState {
    data object Hidden : DialogState()
    data class Show(val message: String, val buttonText: String) : DialogState()
}
