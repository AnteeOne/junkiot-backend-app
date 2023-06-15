package tech.antee.junkiot.features.interfaces.claps_detector.data.mappers

import tech.antee.junkiot.features.interfaces.claps_detector.data.entities.ClapDetectionValueEntity
import tech.antee.junkiot.features.interfaces.claps_detector.domain.models.ClapDetectionValue

class ClapsDetectionValueDomainMapper {

    fun mapToDomain(entity: ClapDetectionValueEntity) = with(entity) { ClapDetectionValue(id, controllerId, timeStamp) }

    fun mapToData(model: ClapDetectionValue) = with(model) { ClapDetectionValueEntity(id, controllerId, timeStamp) }
}