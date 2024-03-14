package com.example.planet.state

sealed class DialogState {
    object Hidden : DialogState()
    data class Show(val message: String, val buttonText: String) : DialogState()
}
