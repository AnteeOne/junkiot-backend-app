package tech.antee.junkiot.features.interfaces.claps_detector.api.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ClapDetectionValueDto(
    val id: Int,
    val controllerId: Int,
    val timeStamp: Long
)
