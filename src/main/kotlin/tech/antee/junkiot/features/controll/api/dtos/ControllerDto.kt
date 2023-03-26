package tech.antee.junkiot.features.controll.api.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ControllerDto(
    val controllerTypeId: Int,
    val id: Int,
    val name: String,
    val isOnline: Boolean
)
