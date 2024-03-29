package tech.antee.junkiot.features.controll.api.controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.websocket.*
import tech.antee.junkiot.features.controll.api.dtos.AddControllerDto
import tech.antee.junkiot.features.controll.api.dtos.ControllerDto
import tech.antee.junkiot.features.controll.api.mappers.ControllerDataMapper
import tech.antee.junkiot.features.controll.data.daos.ControllerDaoImpl
import tech.antee.junkiot.features.controll.data.repositories.ControllerRepositoryImpl
import tech.antee.junkiot.features.controll.domain.repositories.ControllerRepository
import tech.antee.junkiot.features.di.AppComponent

class ControllControllerImpl {

    private val mapper: ControllerDataMapper by lazy { ControllerDataMapper() }

    private val repository: ControllerRepository by lazy { AppComponent.controllerRepository }

    suspend fun getControllers(call: ApplicationCall) {
        val values = repository.getAll().map(mapper::map)
        call.respond(values)
    }

    suspend fun addController(call: ApplicationCall) {
        try {
            val addDto = call.receive<AddControllerDto>()
            val addedValue = repository.add(mapper.mapBack(addDto))
            if (addedValue != null) {
                call.respond(mapper.map(addedValue))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Controller isn't valid!")
            }
        } catch (e: ContentTransformationException) {
            call.respond(HttpStatusCode.BadRequest, "Controller isn't valid!")
        }
    }

    suspend fun updateController(call: ApplicationCall) {
        try {
            val updateDto = call.receive<ControllerDto>()
            val isUpdated = repository.update(mapper.mapBack(updateDto))
            if (isUpdated) {
                call.respond(HttpStatusCode.OK, "Controller updated!")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Controller isn't valid!")
            }
        } catch (e: ContentTransformationException) {
            call.respond(HttpStatusCode.BadRequest, "Controller isn't valid!")
        }
    }

    suspend fun deleteController(call: ApplicationCall) {
        try {
            val controllerId = call.request.queryParameters["id"]?.toInt()
            if (controllerId == null) call.respond(HttpStatusCode.BadRequest, "Controller id isn't valid!")
            val isDeleted = repository.delete(requireNotNull(controllerId))
            if (isDeleted) {
                call.respond(HttpStatusCode.OK, "Controller deleted!")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Controller id isn't valid!")
            }
        } catch (e: ContentTransformationException) {
            call.respond(HttpStatusCode.BadRequest, "Unknown error!")
        }
    }

    suspend fun controllersWebSocket(session: WebSocketServerSession) {
        repository.flow.collect { values ->
            session.sendSerialized(values.map(mapper::map))
        }
    }
}