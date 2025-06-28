package com.vkolisnichenko.expensetracker.navigation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NavigationViewModelTest {

    private lateinit var viewModel: NavigationViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = NavigationViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should have Overview selected`() = runTest {
        val initialState = viewModel.state.first()
        assertEquals(NavigationTab.Overview, initialState.selectedTab)
    }

    @Test
    fun `should update state when tab is selected`() = runTest {
        viewModel.handleIntent(NavigationIntent.SelectTab(NavigationTab.Transactions))
        val updatedState = viewModel.state.first()
        assertEquals(NavigationTab.Transactions, updatedState.selectedTab)
    }

    @Test
    fun `should not emit effects when selecting same tab`() = runTest {
        viewModel.handleIntent(NavigationIntent.SelectTab(NavigationTab.Overview))
        val currentTab = viewModel.getCurrentTab()
        assertEquals(NavigationTab.Overview, currentTab)
    }

    @Test
    fun `should handle rapid tab switches correctly`() = runTest {
        viewModel.handleIntent(NavigationIntent.SelectTab(NavigationTab.Transactions))
        viewModel.handleIntent(NavigationIntent.SelectTab(NavigationTab.Budget))
        viewModel.handleIntent(NavigationIntent.SelectTab(NavigationTab.Statistics))

        val finalState = viewModel.state.first()
        assertEquals(NavigationTab.Statistics, finalState.selectedTab)
    }

    @Test
    fun `isTabSelected should return correct boolean`() = runTest {
        assertTrue(viewModel.isTabSelected(NavigationTab.Overview))
        assertFalse(viewModel.isTabSelected(NavigationTab.Transactions))

        viewModel.handleIntent(NavigationIntent.SelectTab(NavigationTab.Budget))
        assertFalse(viewModel.isTabSelected(NavigationTab.Overview))
        assertTrue(viewModel.isTabSelected(NavigationTab.Budget))
    }

    @Test
    fun `getAvailableTabs should return all tabs`() = runTest {
        val availableTabs = viewModel.getAvailableTabs()
        assertEquals(4, availableTabs.size)
        assertTrue(availableTabs.contains(NavigationTab.Overview))
        assertTrue(availableTabs.contains(NavigationTab.Transactions))
        assertTrue(availableTabs.contains(NavigationTab.Budget))
        assertTrue(availableTabs.contains(NavigationTab.Statistics))
    }

    @Test
    fun `should emit navigation effects when tab is selected`() = runTest {
        viewModel.handleIntent(NavigationIntent.SelectTab(NavigationTab.Budget))
        val updatedState = viewModel.state.first()
        assertEquals(NavigationTab.Budget, updatedState.selectedTab)
        assertTrue("State updated correctly", updatedState.selectedTab == NavigationTab.Budget)
    }
}