package net.brightroom.homepage.data

import bright_room.generated.resources.Res
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

private val json = Json { ignoreUnknownKeys = true }

@OptIn(ExperimentalResourceApi::class)
object ContentLoader {
    suspend fun loadMembers(): List<MemberData> {
        val bytes = Res.readBytes("files/members.json")
        return json.decodeFromString(bytes.decodeToString())
    }

    suspend fun loadProjects(): List<ProjectData> {
        val bytes = Res.readBytes("files/projects.json")
        return json.decodeFromString(bytes.decodeToString())
    }

    suspend fun loadStats(): StatsData {
        val bytes = Res.readBytes("files/stats.json")
        return json.decodeFromString(bytes.decodeToString())
    }

    suspend fun loadTechStack(): TechStackData {
        val bytes = Res.readBytes("files/techstack.json")
        return json.decodeFromString(bytes.decodeToString())
    }
}
