package tech.antee.junkiot.features.integrations.home_assistant.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InterfacesListDto(
    @SerialName("entity_id") val entities: List<String>
)
