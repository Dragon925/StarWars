package com.github.dragon925.starwars.uikit.viewmodel

import androidx.lifecycle.ViewModel
import com.github.dragon925.starwars.uikit.models.UIEvent
import com.github.dragon925.starwars.uikit.models.UIState
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<S: UIState, E: UIEvent> : ViewModel() {

    abstract val state: StateFlow<S>

    abstract fun onEvent(event: E)
}