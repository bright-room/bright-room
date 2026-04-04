package net.brightroom.homepage.shared.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import net.brightroom.homepage.components.NavCategory
import net.brightroom.homepage.components.NavSection

class NavigationState {
    var activeSection by mutableStateOf(NavSection.HOME)
        internal set

    var railHoveredCategory by mutableStateOf<NavCategory?>(null)
        internal set

    var lastRailCategory by mutableStateOf(NavCategory.OVERVIEW)
        internal set

    var railDismissPending by mutableStateOf(false)
        internal set

    var mobileDrawerOpen by mutableStateOf(false)
        internal set

    fun requestRailHover(category: NavCategory?) {
        if (category != null) {
            railDismissPending = false
            railHoveredCategory = category
        } else {
            railDismissPending = true
        }
    }

    fun dismissRail() {
        railHoveredCategory = null
    }

    fun toggleMobileDrawer() {
        mobileDrawerOpen = !mobileDrawerOpen
    }

    fun closeMobileDrawer() {
        mobileDrawerOpen = false
    }

    fun updateActiveSection(firstVisibleItemIndex: Int) {
        activeSection =
            when {
                firstVisibleItemIndex >= 8 -> NavSection.JOIN
                firstVisibleItemIndex >= 7 -> NavSection.FAQ
                firstVisibleItemIndex >= 6 -> NavSection.CONTRIBUTING
                firstVisibleItemIndex >= 5 -> NavSection.TECHSTACK
                firstVisibleItemIndex >= 4 -> NavSection.PROJECTS
                firstVisibleItemIndex >= 3 -> NavSection.MEMBERS
                firstVisibleItemIndex >= 2 -> NavSection.STATS
                firstVisibleItemIndex >= 1 -> NavSection.ABOUT
                else -> NavSection.HOME
            }
    }
}

@Composable
fun rememberNavigationState(): NavigationState {
    val state = remember { NavigationState() }

    LaunchedEffect(state.railHoveredCategory) {
        if (state.railHoveredCategory != null) {
            state.lastRailCategory = state.railHoveredCategory!!
        }
    }

    LaunchedEffect(state.railDismissPending) {
        if (state.railDismissPending) {
            kotlinx.coroutines.delay(120)
            state.railDismissPending = false
            state.railHoveredCategory = null
        }
    }

    return state
}
