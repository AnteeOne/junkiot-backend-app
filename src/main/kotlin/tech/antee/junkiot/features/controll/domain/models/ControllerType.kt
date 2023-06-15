package tech.antee.junkiot.features.controll.domain.models

enum class ControllerType(val id: Int) {
    LIGHT_SENSOR(0),
    CLAPS_DETECTOR(1),
    NOISE_DETECTOR(2)
}

fun Int.parseControllerType(): ControllerType = when (this) {
    ControllerType.LIGHT_SENSOR.id -> ControllerType.LIGHT_SENSOR
    ControllerType.CLAPS_DETECTOR.id -> ControllerType.CLAPS_DETECTOR
    ControllerType.NOISE_DETECTOR.id -> ControllerType.NOISE_DETECTOR
    else -> throw IllegalStateException("Unknown controller id!")
}
