package tech.antee.junkiot.features.light_sensor.api.dtos

import kotlinx.serialization.Serializable

@Serializable
class LightSensorValueDto (
    val id: Int,
    val lx: Int
)