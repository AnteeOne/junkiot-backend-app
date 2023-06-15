package tech.antee.junkiot.features.interfaces.light_sensor.api.dtos

import kotlinx.serialization.Serializable

@Serializable
data class LightSensorPredictionDto(
    val controllerId: Int,
    val lightSensorPredictionValue: String
)