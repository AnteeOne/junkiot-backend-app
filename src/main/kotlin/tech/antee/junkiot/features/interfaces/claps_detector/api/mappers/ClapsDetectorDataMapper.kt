package tech.antee.junkiot.features.interfaces.claps_detector.api.mappers

import tech.antee.junkiot.features.interfaces.claps_detector.api.dtos.ClapDetectionValueDto
import tech.antee.junkiot.features.interfaces.claps_detector.domain.models.ClapDetectionValue

class ClapsDetectorDataMapper {

    fun map(model: ClapDetectionValue): ClapDetectionValueDto = with(model) {
        ClapDetectionValueDto(id, controllerId, timeStamp)
    }
}