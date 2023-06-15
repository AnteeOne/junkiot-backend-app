package tech.antee.junkiot.features.interfaces.light_sensor.api.mappers

import tech.antee.junkiot.features.interfaces.light_sensor.api.dtos.LightSensorPredictionDto
import tech.antee.junkiot.features.interfaces.light_sensor.api.dtos.LightSensorValueDto
import tech.antee.junkiot.features.interfaces.light_sensor.domain.models.LightSensorPrediction
import tech.antee.junkiot.features.interfaces.light_sensor.domain.models.LightSensorValue

class LightSensorDataMapper {

    fun map(model: LightSensorValue): LightSensorValueDto = with(model) {
        LightSensorValueDto(id, lx, controllerId)
    }

    fun map(model: LightSensorPrediction): LightSensorPredictionDto = with(model) {
        LightSensorPredictionDto(controllerId, lightSensorPredictionValue.name)
    }
}