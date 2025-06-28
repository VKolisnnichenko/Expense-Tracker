package com.vkolisnichenko.expensetracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vkolisnichenko.expensetracker.navigation.BottomNavigationBar
import com.vkolisnichenko.expensetracker.navigation.NavigationEffect
import com.vkolisnichenko.expensetracker.navigation.NavigationIntent
import com.vkolisnichenko.expensetracker.navigation.NavigationTab
import com.vkolisnichenko.expensetracker.navigation.NavigationViewModel
import com.vkolisnichenko.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                ExpenseTrackerApp()
            }
        }
    }
}

@Composable
fun ExpenseTrackerApp(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel = viewModel()
) {
    val navController = rememberNavController()
    val navigationState by navigationViewModel.state.collectAsState()

    LaunchedEffect(navigationViewModel) {
        navigationViewModel.effects.collect { effect ->
            when (effect) {
                is NavigationEffect.NavigateToTab -> {
                    navController.navigate(effect.tab.route) {
                        popUpTo(NavigationTab.Overview.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }

                is NavigationEffect.ShowTabSwitchAnimation -> {
                }
            }
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    selectedTab = navigationState.selectedTab,
                    onTabSelected = { tab ->
                        navigationViewModel.handleIntent(NavigationIntent.SelectTab(tab))
                    }
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = NavigationTab.Overview.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(NavigationTab.Overview.route) {
                    OverviewScreen()
                }
                composable(NavigationTab.Transactions.route) {
                    TransactionsScreen()
                }
                composable(NavigationTab.Budget.route) {
                    BudgetScreen()
                }
                composable(NavigationTab.Statistics.route) {
                    StatisticsScreen()
                }
            }
        }
    }
}

@Composable
fun OverviewScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        androidx.compose.foundation.layout.Box(
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "Overview Screen",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun TransactionsScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        androidx.compose.foundation.layout.Box(
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "Transactions Screen",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun BudgetScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        androidx.compose.foundation.layout.Box(
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "Budget Screen",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun StatisticsScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        androidx.compose.foundation.layout.Box(
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "Statistics Screen",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseTrackerAppPreview() {
    ExpenseTrackerTheme {
        ExpenseTrackerApp()
    }
}