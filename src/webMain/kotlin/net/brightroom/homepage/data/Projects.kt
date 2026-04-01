package net.brightroom.homepage.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectData(
    val name: String,
    @SerialName("description_key") val descriptionKey: String,
    val tags: List<String>,
    val stars: Int = 0,
    @SerialName("github_url") val githubUrl: String,
)
