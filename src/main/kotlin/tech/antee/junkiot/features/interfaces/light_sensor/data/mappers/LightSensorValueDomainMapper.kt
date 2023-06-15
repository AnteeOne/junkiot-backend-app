package tech.antee.junkiot.features.interfaces.light_sensor.data.mappers

import tech.antee.junkiot.features.interfaces.light_sensor.data.entities.LightSensorValueEntity
import tech.antee.junkiot.features.interfaces.light_sensor.domain.models.LightSensorValue

class LightSensorValueDomainMapper {

    fun mapToDomain(entity: LightSensorValueEntity) = with(entity) { LightSensorValue(id, lx, controllerId) }

    fun mapToData(model: LightSensorValue) = with(model) { LightSensorValueEntity(id, lx, controllerId) }
}