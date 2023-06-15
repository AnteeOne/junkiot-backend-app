package tech.antee.junkiot.features.interfaces.light_sensor.api.dtos

import kotlinx.serialization.Serializable

@Serializable
class AddLightSensorValueDto(
    val controllerId: Int,
    val lx: Int,
)