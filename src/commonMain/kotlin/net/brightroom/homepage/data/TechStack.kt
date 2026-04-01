package net.brightroom.homepage.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TechStackData(val categories: List<TechCategoryData> = emptyList(), val items: List<TechItemData> = emptyList())

@Serializable
data class TechCategoryData(
    val id: String,
    @SerialName("label_key") val labelKey: String,
)

@Serializable
data class TechItemData(
    val name: String,
    val category: String,
    val color: String,
)
