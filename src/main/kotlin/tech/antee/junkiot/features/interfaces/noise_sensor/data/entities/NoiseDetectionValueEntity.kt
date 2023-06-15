package tech.antee.junkiot.features.interfaces.noise_sensor.data.entities

data class NoiseDetectionValueEntity(
    val id: Int,
    val controllerId: Int,
    val audioLabel: String,
    val timeStamp: Long
)
