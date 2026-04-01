package net.brightroom.homepage.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberData(
    val name: String,
    @SerialName("role_key") val roleKey: String,
    @SerialName("avatar_initials") val avatarInitials: String,
    @SerialName("github_url") val githubUrl: String,
)
