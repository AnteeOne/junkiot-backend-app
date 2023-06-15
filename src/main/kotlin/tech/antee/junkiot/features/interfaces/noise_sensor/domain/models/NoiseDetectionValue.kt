package tech.antee.junkiot.features.interfaces.noise_sensor.domain.models

data class NoiseDetectionValue(
    val id: Int,
    val controllerId: Int,
    val audioLabel: String,
    val timeStamp: Long
)
