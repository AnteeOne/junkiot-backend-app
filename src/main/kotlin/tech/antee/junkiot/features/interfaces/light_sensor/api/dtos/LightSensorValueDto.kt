package tech.antee.junkiot.features.interfaces.light_sensor.api.dtos

import kotlinx.serialization.Serializable

@Serializable
class LightSensorValueDto (
    val id: Int,
    val lx: Int,
    val controllerId: Int
)