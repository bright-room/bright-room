package net.brightroom.homepage.app

import androidx.compose.ui.unit.dp
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AppViewModelTest {
    @Test
    fun toggleTheme_switchesBetweenDarkAndLight() {
        val vm = AppViewModel()
        assertTrue(vm.isDarkTheme)
        vm.toggleTheme()
        assertFalse(vm.isDarkTheme)
        vm.toggleTheme()
        assertTrue(vm.isDarkTheme)
    }

    @Test
    fun toggleLanguage_switchesBetweenJapaneseAndEnglish() {
        val vm = AppViewModel()
        assertTrue(vm.isJapanese)
        vm.toggleLanguage()
        assertFalse(vm.isJapanese)
        vm.toggleLanguage()
        assertTrue(vm.isJapanese)
    }

    @Test
    fun updateWindowSize_compactBelow600() {
        val vm = AppViewModel()
        vm.updateWindowSize(599, 800.dp)
        assertEquals(WindowSizeClass.COMPACT, vm.windowSizeClass)
    }

    @Test
    fun updateWindowSize_mediumAt600() {
        val vm = AppViewModel()
        vm.updateWindowSize(600, 800.dp)
        assertEquals(WindowSizeClass.MEDIUM, vm.windowSizeClass)
    }

    @Test
    fun updateWindowSize_mediumBelow893() {
        val vm = AppViewModel()
        vm.updateWindowSize(892, 800.dp)
        assertEquals(WindowSizeClass.MEDIUM, vm.windowSizeClass)
    }

    @Test
    fun updateWindowSize_expandedAt893() {
        val vm = AppViewModel()
        vm.updateWindowSize(893, 800.dp)
        assertEquals(WindowSizeClass.EXPANDED, vm.windowSizeClass)
    }

    @Test
    fun updateWindowSize_expandedBelow1200() {
        val vm = AppViewModel()
        vm.updateWindowSize(1199, 800.dp)
        assertEquals(WindowSizeClass.EXPANDED, vm.windowSizeClass)
    }

    @Test
    fun updateWindowSize_wideAt1200() {
        val vm = AppViewModel()
        vm.updateWindowSize(1200, 800.dp)
        assertEquals(WindowSizeClass.WIDE, vm.windowSizeClass)
    }

    @Test
    fun updateWindowSize_updatesHeightDp() {
        val vm = AppViewModel()
        vm.updateWindowSize(1000, 500.dp)
        assertEquals(500.dp, vm.windowHeightDp)
    }

    @Test
    fun initialState_isLoadingTrue() {
        val vm = AppViewModel()
        assertTrue(vm.isLoading)
    }

    @Test
    fun initialState_defaultWindowSizeIsWide() {
        val vm = AppViewModel()
        assertEquals(WindowSizeClass.WIDE, vm.windowSizeClass)
    }
}
