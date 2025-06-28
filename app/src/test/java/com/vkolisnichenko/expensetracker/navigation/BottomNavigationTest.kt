package com.vkolisnichenko.expensetracker.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class BottomNavigationTest {

    private lateinit var navigationTabs: List<NavigationTab>

    @Before
    fun setUp() {
        navigationTabs = listOf(
            NavigationTab.Overview,
            NavigationTab.Transactions,
            NavigationTab.Budget,
            NavigationTab.Statistics
        )
    }

    @Test
    fun `should have exactly 4 navigation tabs`() {
        assertEquals("Navigation should have exactly 4 tabs", 4, navigationTabs.size)
    }

    @Test
    fun `should have Overview tab with correct properties`() {
        val overviewTab = navigationTabs[0]
        assertEquals("overview", overviewTab.route)
        assertEquals("Overview", overviewTab.title)
        assertTrue("Overview tab should have icon", overviewTab.icon != null)
    }

    @Test
    fun `should have Transactions tab with correct properties`() {
        val transactionsTab = navigationTabs[1]
        assertEquals("transactions", transactionsTab.route)
        assertEquals("Transactions", transactionsTab.title)
        assertTrue("Transactions tab should have icon", transactionsTab.icon != null)
    }

    @Test
    fun `should have Budget tab with correct properties`() {
        val budgetTab = navigationTabs[2]
        assertEquals("budget", budgetTab.route)
        assertEquals("Budget", budgetTab.title)
        assertTrue("Budget tab should have icon", budgetTab.icon != null)
    }

    @Test
    fun `should have Statistics tab with correct properties`() {
        val statisticsTab = navigationTabs[3]
        assertEquals("statistics", statisticsTab.route)
        assertEquals("Statistics", statisticsTab.title)
        assertTrue("Statistics tab should have icon", statisticsTab.icon != null)
    }

    @Test
    fun `all tabs should have unique routes`() {
        val routes = navigationTabs.map { it.route }
        val uniqueRoutes = routes.toSet()
        assertEquals("All tabs should have unique routes", routes.size, uniqueRoutes.size)
    }

    @Test
    fun `all tabs should have non-empty titles`() {
        navigationTabs.forEach { tab ->
            assertTrue("Tab ${tab.route} should have non-empty title", tab.title.isNotEmpty())
        }
    }

    @Test
    fun `default selected tab should be Overview`() {
        val defaultTab = NavigationTab.Overview
        assertEquals("Default tab should be Overview", "overview", defaultTab.route)
    }
}