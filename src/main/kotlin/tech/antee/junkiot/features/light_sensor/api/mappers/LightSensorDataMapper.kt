package tech.antee.junkiot.features.light_sensor.api.mappers

import tech.antee.junkiot.features.light_sensor.api.dtos.LightSensorValueDto
import tech.antee.junkiot.features.light_sensor.domain.models.LightSensorValue

class LightSensorDataMapper {

    fun map(model: LightSensorValue): LightSensorValueDto = with(model) {
        LightSensorValueDto(id, lx)
    }
}