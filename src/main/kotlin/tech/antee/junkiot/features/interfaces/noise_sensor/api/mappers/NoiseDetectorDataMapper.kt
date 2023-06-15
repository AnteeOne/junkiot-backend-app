package tech.antee.junkiot.features.interfaces.noise_sensor.api.mappers

import tech.antee.junkiot.features.interfaces.noise_sensor.api.dtos.NoiseDetectionValueDto
import tech.antee.junkiot.features.interfaces.noise_sensor.domain.models.NoiseDetectionValue

class NoiseDetectorDataMapper {

    fun map(model: NoiseDetectionValue): NoiseDetectionValueDto = with(model) {
        NoiseDetectionValueDto(id, controllerId, audioLabel, timeStamp)
    }
}