package net.brightroom.homepage.data

enum class RoadmapStatus {
    CURRENT,
    UPCOMING,
    PLANNED,
}

data class RoadmapItem(
    val quarter: String,
    val status: RoadmapStatus,
    val items: List<String>,
)

object RoadmapParser {
    private val headerPattern = Regex("""^##\s+(.+?)\s+\[(\w+)\]""")

    fun parse(markdown: String): List<RoadmapItem> {
        val result = mutableListOf<RoadmapItem>()
        var currentQuarter: String? = null
        var currentStatus: RoadmapStatus? = null
        var currentItems = mutableListOf<String>()

        for (line in markdown.lines()) {
            val headerMatch = headerPattern.find(line)
            if (headerMatch != null) {
                if (currentQuarter != null && currentStatus != null) {
                    result.add(RoadmapItem(currentQuarter, currentStatus, currentItems.toList()))
                }
                currentQuarter = headerMatch.groupValues[1]
                currentStatus = when (headerMatch.groupValues[2].lowercase()) {
                    "current" -> RoadmapStatus.CURRENT
                    "upcoming" -> RoadmapStatus.UPCOMING
                    else -> RoadmapStatus.PLANNED
                }
                currentItems = mutableListOf()
            } else if (line.trimStart().startsWith("- ")) {
                currentItems.add(line.trimStart().removePrefix("- "))
            }
        }

        if (currentQuarter != null && currentStatus != null) {
            result.add(RoadmapItem(currentQuarter, currentStatus, currentItems.toList()))
        }

        return result
    }
}
