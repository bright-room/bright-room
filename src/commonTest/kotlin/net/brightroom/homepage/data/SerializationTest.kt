package net.brightroom.homepage.data

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SerializationTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun memberData_deserializesCorrectly() {
        val raw =
            """
            {
                "name": "Test User",
                "role_key": "role_owner",
                "avatar_initials": "TU",
                "github_url": "https://github.com/test"
            }
            """.trimIndent()
        val member = json.decodeFromString<MemberData>(raw)
        assertEquals("Test User", member.name)
        assertEquals("role_owner", member.roleKey)
        assertEquals("TU", member.avatarInitials)
        assertEquals("https://github.com/test", member.githubUrl)
    }

    @Test
    fun memberData_listDeserializesCorrectly() {
        val raw =
            """
            [
                {"name":"A","role_key":"role_owner","avatar_initials":"AA","github_url":"https://github.com/a"},
                {"name":"B","role_key":"role_frontend","avatar_initials":"BB","github_url":"https://github.com/b"}
            ]
            """.trimIndent()
        val members = json.decodeFromString<List<MemberData>>(raw)
        assertEquals(2, members.size)
        assertEquals("A", members[0].name)
        assertEquals("B", members[1].name)
    }

    @Test
    fun projectData_deserializesWithDefaults() {
        val raw =
            """
            {
                "name": "my-project",
                "description_key": "project_desc",
                "tags": ["Kotlin", "Gradle"],
                "github_url": "https://github.com/org/repo"
            }
            """.trimIndent()
        val project = json.decodeFromString<ProjectData>(raw)
        assertEquals("my-project", project.name)
        assertEquals(0, project.stars)
        assertEquals(listOf("Kotlin", "Gradle"), project.tags)
    }

    @Test
    fun projectData_deserializesWithStars() {
        val raw =
            """
            {
                "name": "starred",
                "description_key": "desc",
                "tags": [],
                "stars": 42,
                "github_url": "https://github.com/org/starred"
            }
            """.trimIndent()
        val project = json.decodeFromString<ProjectData>(raw)
        assertEquals(42, project.stars)
    }

    @Test
    fun statsData_deserializesAllFields() {
        val raw =
            """
            {
                "repositories": 12,
                "contributors": 24,
                "total_commits": 3480,
                "open_prs": 18,
                "closed_issues": 342,
                "total_stars": 648
            }
            """.trimIndent()
        val stats = json.decodeFromString<StatsData>(raw)
        assertEquals(12, stats.repositories)
        assertEquals(24, stats.contributors)
        assertEquals(3480, stats.totalCommits)
        assertEquals(18, stats.openPrs)
        assertEquals(342, stats.closedIssues)
        assertEquals(648, stats.totalStars)
    }

    @Test
    fun statsData_defaultsToZero() {
        val stats = json.decodeFromString<StatsData>("{}")
        assertEquals(0, stats.repositories)
        assertEquals(0, stats.contributors)
        assertEquals(0, stats.totalCommits)
        assertEquals(0, stats.openPrs)
        assertEquals(0, stats.closedIssues)
        assertEquals(0, stats.totalStars)
    }

    @Test
    fun techStackData_deserializesCorrectly() {
        val raw =
            """
            {
                "categories": [
                    {"id": "LANGUAGE", "label_key": "tech_cat_language"}
                ],
                "items": [
                    {"name": "Kotlin", "category": "LANGUAGE", "color": "#7f52ff"}
                ]
            }
            """.trimIndent()
        val techStack = json.decodeFromString<TechStackData>(raw)
        assertEquals(1, techStack.categories.size)
        assertEquals("LANGUAGE", techStack.categories[0].id)
        assertEquals(1, techStack.items.size)
        assertEquals("Kotlin", techStack.items[0].name)
        assertEquals("#7f52ff", techStack.items[0].color)
    }

    @Test
    fun techStackData_defaultsToEmptyLists() {
        val techStack = json.decodeFromString<TechStackData>("{}")
        assertTrue(techStack.categories.isEmpty())
        assertTrue(techStack.items.isEmpty())
    }

    @Test
    fun memberData_ignoresUnknownKeys() {
        val raw =
            """
            {
                "name": "Test",
                "role_key": "role_owner",
                "avatar_initials": "TT",
                "github_url": "https://github.com/test",
                "unknown_field": "should be ignored"
            }
            """.trimIndent()
        val member = json.decodeFromString<MemberData>(raw)
        assertEquals("Test", member.name)
    }
}
