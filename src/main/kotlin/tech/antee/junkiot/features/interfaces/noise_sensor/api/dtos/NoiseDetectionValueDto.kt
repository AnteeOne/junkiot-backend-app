package tech.antee.junkiot.features.interfaces.noise_sensor.api.dtos

import kotlinx.serialization.Serializable

@Serializable
data class NoiseDetectionValueDto(
    val id: Int,
    val controllerId: Int,
    val label: String,
    val timeStamp: Long
)
