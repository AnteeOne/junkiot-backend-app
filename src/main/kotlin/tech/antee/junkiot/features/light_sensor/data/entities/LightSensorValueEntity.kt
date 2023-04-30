package tech.antee.junkiot.features.light_sensor.data.entities

data class LightSensorValueEntity(
    val id: Int,
    val lx: Int,
    val controllerId: Int
)
