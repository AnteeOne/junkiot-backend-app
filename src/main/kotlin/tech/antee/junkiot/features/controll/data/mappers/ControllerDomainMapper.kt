package tech.antee.junkiot.features.controll.data.mappers

import tech.antee.junkiot.features.controll.data.entities.ControllerEntity
import tech.antee.junkiot.features.controll.domain.models.Controller
import tech.antee.junkiot.features.controll.domain.models.parseControllerType

class ControllerDomainMapper {

    fun mapToDomain(entity: ControllerEntity): Controller = with(entity) {
        Controller(
            id = id,
            controllerType = controllerType.parseControllerType(),
            name = name,
            isOnline = isOnline
        )
    }

    fun mapToData(model: Controller): ControllerEntity = with(model) {
        ControllerEntity(
            id = id,
            controllerType = controllerType.id,
            name = name,
            isOnline = isOnline
        )
    }
}