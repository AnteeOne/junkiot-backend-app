package tech.antee.junkiot.features.interfaces.light_sensor.domain.models

data class LightSensorValue(
    val id: Int,
    val lx: Int,
    val controllerId: Int
)
