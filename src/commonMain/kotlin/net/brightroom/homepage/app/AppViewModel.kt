package net.brightroom.homepage.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _members = MutableStateFlow<List<MemberData>>(emptyList())
    val members: StateFlow<List<MemberData>> = _members

    private val _projects = MutableStateFlow<List<ProjectData>>(emptyList())
    val projects: StateFlow<List<ProjectData>> = _projects

    private val _stats = MutableStateFlow(StatsData())
    val stats: StateFlow<StatsData> = _stats

    private val _techStack = MutableStateFlow(TechStackData())
    val techStack: StateFlow<TechStackData> = _techStack

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
        _members.value = ContentLoader.loadMembers()
        _projects.value = ContentLoader.loadProjects()
        _stats.value = ContentLoader.loadStats()
        _techStack.value = ContentLoader.loadTechStack()
        isLoading = false
    }
}
