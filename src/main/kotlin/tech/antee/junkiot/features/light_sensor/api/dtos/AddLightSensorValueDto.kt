package tech.antee.junkiot.features.light_sensor.api.dtos

import kotlinx.serialization.Serializable

@Serializable
class AddLightSensorValueDto(
    val lx: Int
)