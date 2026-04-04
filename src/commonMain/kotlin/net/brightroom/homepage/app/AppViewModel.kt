package net.brightroom.homepage.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import net.brightroom.homepage.data.ContentLoader
import net.brightroom.homepage.data.MemberData
import net.brightroom.homepage.data.ProjectData
import net.brightroom.homepage.data.StatsData
import net.brightroom.homepage.data.TechStackData

enum class WindowSizeClass {
    COMPACT,
    MEDIUM,
    EXPANDED,
    WIDE,
}

class AppViewModel : ViewModel() {
    var isLoading by mutableStateOf(true)
        private set

    var isDarkTheme by mutableStateOf(true)
        private set

    var isJapanese by mutableStateOf(true)
        private set

    var windowSizeClass by mutableStateOf(WindowSizeClass.WIDE)
        private set

    var windowHeightDp by mutableStateOf(700.dp)
        private set

    var members by mutableStateOf<List<MemberData>>(emptyList())
        private set

    var projects by mutableStateOf<List<ProjectData>>(emptyList())
        private set

    var stats by mutableStateOf(StatsData())
        private set

    var techStack by mutableStateOf(TechStackData())
        private set

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
    }

    fun toggleLanguage() {
        isJapanese = !isJapanese
    }

    fun updateWindowSize(
        widthDp: Int,
        heightDp: Dp,
    ) {
        windowSizeClass =
            when {
                widthDp < 600 -> WindowSizeClass.COMPACT
                widthDp < 893 -> WindowSizeClass.MEDIUM
                widthDp < 1200 -> WindowSizeClass.EXPANDED
                else -> WindowSizeClass.WIDE
            }
        windowHeightDp = heightDp
    }

    suspend fun loadContent() {
        members = ContentLoader.loadMembers()
        projects = ContentLoader.loadProjects()
        stats = ContentLoader.loadStats()
        techStack = ContentLoader.loadTechStack()
        isLoading = false
    }
}
