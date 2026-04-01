package net.brightroom.homepage.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatsData(
    val repositories: Int = 0,
    val contributors: Int = 0,
    @SerialName("total_commits") val totalCommits: Int = 0,
    @SerialName("open_prs") val openPrs: Int = 0,
    @SerialName("closed_issues") val closedIssues: Int = 0,
    @SerialName("total_stars") val totalStars: Int = 0,
)
