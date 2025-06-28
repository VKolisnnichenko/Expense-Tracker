package com.vkolisnichenko.expensetracker.navigation

import com.vkolisnichenko.expensetracker.R

sealed class NavigationTab(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Overview : NavigationTab(
        route = "overview",
        title = "Overview",
        icon = R.drawable.ic_chart
    )

    object Transactions : NavigationTab(
        route = "transactions",
        title = "Transactions",
        icon = R.drawable.ic_credit_card
    )

    object Budget : NavigationTab(
        route = "budget",
        title = "Budget",
        icon = R.drawable.ic_wallet,
    )

    object Statistics : NavigationTab(
        route = "statistics",
        title = "Statistics",
        icon = R.drawable.ic_trending_up
    )

    companion object {
        val allTabs: List<NavigationTab> by lazy {
            listOf(Overview, Transactions, Budget, Statistics)
        }

        fun fromRoute(route: String): NavigationTab? {
            return allTabs.find { it.route == route }
        }
    }
}

data class NavigationState(
    val selectedTab: NavigationTab = NavigationTab.Overview,
    val availableTabs: List<NavigationTab> = NavigationTab.allTabs
)

sealed class NavigationIntent {
    data class SelectTab(val tab: NavigationTab) : NavigationIntent()
}

sealed class NavigationEffect {
    data class NavigateToTab(val tab: NavigationTab) : NavigationEffect()
    object ShowTabSwitchAnimation : NavigationEffect()
}