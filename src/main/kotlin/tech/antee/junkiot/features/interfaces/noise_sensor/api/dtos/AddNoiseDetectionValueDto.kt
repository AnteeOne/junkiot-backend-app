package tech.antee.junkiot.features.interfaces.noise_sensor.api.dtos

import kotlinx.serialization.Serializable

@Serializable
class AddNoiseDetectionValueDto(
    val controllerId: Int,
    val label: String
)