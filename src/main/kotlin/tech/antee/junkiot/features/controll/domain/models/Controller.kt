package tech.antee.junkiot.features.controll.domain.models

data class Controller(
    val id: Int,
    val controllerType: ControllerType,
    val name: String,
    val isOnline: Boolean
)