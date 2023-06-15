package tech.antee.junkiot.features.interfaces.light_sensor.domain.models

data class LightSensorPrediction(
    val controllerId: Int,
    val lightSensorPredictionValue: LightSensorPredictionValue
)