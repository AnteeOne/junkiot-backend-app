package tech.antee.junkiot.features.controll.api.mappers

import tech.antee.junkiot.features.controll.api.dtos.AddControllerDto
import tech.antee.junkiot.features.controll.api.dtos.ControllerDto
import tech.antee.junkiot.features.controll.domain.models.AddController
import tech.antee.junkiot.features.controll.domain.models.Controller
import tech.antee.junkiot.features.controll.domain.models.parseControllerType

class ControllerDataMapper {

    fun map(model: Controller): ControllerDto = with(model) {
        ControllerDto(
            controllerTypeId = controllerType.id,
            id = id,
            name = name,
            isOnline = isOnline
        )
    }

    fun mapBack(dto: ControllerDto): Controller = with(dto) {
        Controller(
            id = id,
            controllerType = controllerTypeId.parseControllerType(),
            name = name,
            isOnline = isOnline
        )
    }

    fun mapBack(dto: AddControllerDto): AddController = with(dto) {
        AddController(
            controllerType = typeId.parseControllerType(),
            name = name
        )
    }
}