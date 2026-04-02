package net.brightroom.homepage.shared.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import bright_room.generated.resources.Res
import bright_room.generated.resources.nav_about
import bright_room.generated.resources.nav_cat_overview
import bright_room.generated.resources.nav_cat_participate
import bright_room.generated.resources.nav_cat_support
import bright_room.generated.resources.nav_cat_works
import bright_room.generated.resources.nav_contributing
import bright_room.generated.resources.nav_faq
import bright_room.generated.resources.nav_home
import bright_room.generated.resources.nav_join
import bright_room.generated.resources.nav_members
import bright_room.generated.resources.nav_projects
import bright_room.generated.resources.nav_roadmap
import bright_room.generated.resources.nav_stats
import bright_room.generated.resources.nav_tech
import kotlinx.coroutines.launch
import net.brightroom.homepage.app.LocalAppViewModel
import net.brightroom.homepage.app.WindowSizeClass
import net.brightroom.homepage.components.BackToTopButton
import net.brightroom.homepage.components.Footer
import net.brightroom.homepage.components.MobileNavigationMenu
import net.brightroom.homepage.components.NavCategory
import net.brightroom.homepage.components.NavSection
import net.brightroom.homepage.components.SideNavIndicator
import net.brightroom.homepage.components.TopBar
import net.brightroom.homepage.screens.about.AboutSection
import net.brightroom.homepage.screens.contributing.ContributingSection
import net.brightroom.homepage.screens.faq.FaqSection
import net.brightroom.homepage.screens.hero.HeroSection
import net.brightroom.homepage.screens.join.JoinSection
import net.brightroom.homepage.screens.members.MembersSection
import net.brightroom.homepage.screens.projects.ProjectsSection
import net.brightroom.homepage.screens.roadmap.RoadmapSection
import net.brightroom.homepage.screens.stats.StatsSection
import net.brightroom.homepage.screens.techstack.TechStackSection
import org.jetbrains.compose.resources.stringResource

@Composable
fun Layout() {
    val viewModel = LocalAppViewModel.current
    val density = LocalDensity.current
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var mobileMenuOpen by remember { mutableStateOf(false) }
    var activeSection by remember { mutableStateOf(NavSection.HOME) }

    val homeLabel = stringResource(Res.string.nav_home)
    val aboutLabel = stringResource(Res.string.nav_about)
    val statsLabel = stringResource(Res.string.nav_stats)
    val membersLabel = stringResource(Res.string.nav_members)
    val projectsLabel = stringResource(Res.string.nav_projects)
    val techLabel = stringResource(Res.string.nav_tech)
    val contribLabel = stringResource(Res.string.nav_contributing)
    val roadmapLabel = stringResource(Res.string.nav_roadmap)
    val faqLabel = stringResource(Res.string.nav_faq)
    val joinLabel = stringResource(Res.string.nav_join)

    val navLabels =
        remember(
            homeLabel,
            aboutLabel,
            statsLabel,
            membersLabel,
            projectsLabel,
            techLabel,
            contribLabel,
            roadmapLabel,
            faqLabel,
            joinLabel,
        ) {
            mapOf(
                NavSection.HOME to homeLabel,
                NavSection.ABOUT to aboutLabel,
                NavSection.STATS to statsLabel,
                NavSection.MEMBERS to membersLabel,
                NavSection.PROJECTS to projectsLabel,
                NavSection.TECHSTACK to techLabel,
                NavSection.CONTRIBUTING to contribLabel,
                NavSection.ROADMAP to roadmapLabel,
                NavSection.FAQ to faqLabel,
                NavSection.JOIN to joinLabel,
            )
        }

    val overviewLabel = stringResource(Res.string.nav_cat_overview)
    val worksLabel = stringResource(Res.string.nav_cat_works)
    val participateLabel = stringResource(Res.string.nav_cat_participate)
    val supportLabel = stringResource(Res.string.nav_cat_support)

    val categoryLabels =
        remember(overviewLabel, worksLabel, participateLabel, supportLabel) {
            mapOf(
                NavCategory.OVERVIEW to overviewLabel,
                NavCategory.WORKS to worksLabel,
                NavCategory.PARTICIPATE to participateLabel,
                NavCategory.SUPPORT to supportLabel,
            )
        }

    // Track active section based on scroll position
    LaunchedEffect(listState.firstVisibleItemIndex) {
        val index = listState.firstVisibleItemIndex
        activeSection =
            when {
                index >= 9 -> NavSection.JOIN
                index >= 8 -> NavSection.FAQ
                index >= 7 -> NavSection.ROADMAP
                index >= 6 -> NavSection.CONTRIBUTING
                index >= 5 -> NavSection.TECHSTACK
                index >= 4 -> NavSection.PROJECTS
                index >= 3 -> NavSection.MEMBERS
                index >= 2 -> NavSection.STATS
                index >= 1 -> NavSection.ABOUT
                else -> NavSection.HOME
            }
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .onSizeChanged { size ->
                    with(density) {
                        val widthDp =
                            size.width
                                .toDp()
                                .value
                                .toInt()
                        val heightDp = size.height.toDp()
                        viewModel.updateWindowSize(widthDp, heightDp)
                    }
                },
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    onHomeClick = {
                        scope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    onMenuClick = { mobileMenuOpen = true },
                )
            },
        ) { padding ->
            Box(Modifier.fillMaxSize().padding(padding)) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item {
                        HeroSection(
                            onJoinClick = {
                                scope.launch {
                                    listState.animateScrollToItem(NavSection.entries.indexOf(NavSection.JOIN))
                                }
                            },
                        )
                    }
                    item { AboutSection() }
                    item { StatsSection() }
                    item { MembersSection() }
                    item { ProjectsSection() }
                    item { TechStackSection() }
                    item { ContributingSection() }
                    item { RoadmapSection() }
                    item { FaqSection() }
                    item { JoinSection() }
                    item { Footer() }
                }

                // Side navigation indicator (desktop only)
                if (viewModel.windowSizeClass == WindowSizeClass.WIDE) {
                    SideNavIndicator(
                        activeSection = activeSection,
                        navLabels = navLabels,
                        onNavClick = { section ->
                            scope.launch {
                                val index = NavSection.entries.indexOf(section)
                                listState.animateScrollToItem(index)
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterStart),
                    )
                }

                // Back to top button (bottom-left, stays above footer, WIDE only)
                if (viewModel.windowSizeClass == WindowSizeClass.WIDE && activeSection != NavSection.HOME) {
                    val footerIndex = NavSection.entries.size
                    val layoutInfo = listState.layoutInfo
                    val footerItem = layoutInfo.visibleItemsInfo.firstOrNull { it.index == footerIndex }
                    val viewportHeight = layoutInfo.viewportSize.height
                    val bottomPadding =
                        if (footerItem != null) {
                            val footerVisibleHeight = viewportHeight - footerItem.offset
                            with(density) { footerVisibleHeight.toDp() } + 24.dp
                        } else {
                            24.dp
                        }

                    BackToTopButton(
                        onClick = {
                            scope.launch {
                                listState.animateScrollToItem(0)
                            }
                        },
                        modifier =
                            Modifier
                                .align(Alignment.BottomStart)
                                .padding(start = 24.dp, bottom = bottomPadding),
                    )
                }
            }
        }

        // Mobile menu overlay
        AnimatedVisibility(
            visible = mobileMenuOpen,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            MobileNavigationMenu(
                navLabels = navLabels,
                categoryLabels = categoryLabels,
                onNavClick = { section ->
                    scope.launch {
                        val index = NavSection.entries.indexOf(section)
                        listState.animateScrollToItem(index)
                    }
                },
                onClose = { mobileMenuOpen = false },
            )
        }
    }
}
