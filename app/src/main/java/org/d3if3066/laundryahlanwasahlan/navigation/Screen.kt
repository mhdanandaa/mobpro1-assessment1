package org.d3if3066.laundryahlanwasahlan.navigation

sealed class Screen(val route: String) {

    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
}