package com.vkolisnichenko.expensetracker.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        NavigationState(
            selectedTab = NavigationTab.Overview,
            availableTabs = NavigationTab.allTabs
        )
    )
    val state: StateFlow<NavigationState> = _state.asStateFlow()

    private val _effects = Channel<NavigationEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun handleIntent(intent: NavigationIntent) {
        when (intent) {
            is NavigationIntent.SelectTab -> {
                selectTab(intent.tab)
            }
        }
    }

    private fun selectTab(tab: NavigationTab) {
        val currentState = _state.value

        if (currentState.selectedTab != tab) {
            val newState = currentState.copy(selectedTab = tab)
            _state.value = newState

            viewModelScope.launch {
                _effects.send(NavigationEffect.NavigateToTab(tab))
                _effects.send(NavigationEffect.ShowTabSwitchAnimation)
            }
        }
    }

    fun getCurrentTab(): NavigationTab {
        return _state.value.selectedTab
    }

    fun isTabSelected(tab: NavigationTab): Boolean {
        return _state.value.selectedTab == tab
    }

    fun getAvailableTabs(): List<NavigationTab> {
        return _state.value.availableTabs
    }
}