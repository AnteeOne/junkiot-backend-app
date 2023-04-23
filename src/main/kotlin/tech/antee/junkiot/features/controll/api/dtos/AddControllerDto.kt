package tech.antee.junkiot.features.controll.api.dtos

import kotlinx.serialization.Serializable

@Serializable
data class AddControllerDto(
    val typeId: Int,
    val name: String
)
