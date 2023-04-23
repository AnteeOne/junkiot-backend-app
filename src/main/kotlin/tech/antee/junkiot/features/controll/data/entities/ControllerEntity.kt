package tech.antee.junkiot.features.controll.data.entities

data class ControllerEntity(
    val id: Int,
    val controllerType: Int,
    val name: String,
    val isOnline: Boolean
)