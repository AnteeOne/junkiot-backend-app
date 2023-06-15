package tech.antee.junkiot.features.interfaces.noise_sensor.data.mappers

import tech.antee.junkiot.features.interfaces.noise_sensor.data.entities.NoiseDetectionValueEntity
import tech.antee.junkiot.features.interfaces.noise_sensor.domain.models.NoiseDetectionValue


class NoiseDetectionValueDomainMapper {

    fun mapToDomain(entity: NoiseDetectionValueEntity) =
        with(entity) { NoiseDetectionValue(id, controllerId, audioLabel, timeStamp) }

    fun mapToData(model: NoiseDetectionValue) =
        with(model) { NoiseDetectionValueEntity(id, controllerId, audioLabel, timeStamp) }
}