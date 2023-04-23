package tech.antee.junkiot.features.light_sensor.api.controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.websocket.*
import tech.antee.junkiot.features.light_sensor.api.dtos.AddLightSensorValueDto
import tech.antee.junkiot.features.light_sensor.api.dtos.LightSensorValueDto
import tech.antee.junkiot.features.light_sensor.api.mappers.LightSensorDataMapper
import tech.antee.junkiot.features.light_sensor.data.daos.LightSensorValuesDaoImpl
import tech.antee.junkiot.features.light_sensor.data.repositories.LightSensorValueRepositoryImpl

class LightSensorControllerImpl {

    private val mapper: LightSensorDataMapper by lazy { LightSensorDataMapper() }

    private val repository by lazy { LightSensorValueRepositoryImpl(LightSensorValuesDaoImpl()) }

    suspend fun getLightSensorValues(call: ApplicationCall) {
        val values = repository.getAll().map(mapper::map)
        call.respond(values)
    }

    suspend fun addLightSensorValue(call: ApplicationCall) {
        try {
            val addDto = call.receive<AddLightSensorValueDto>()
            val addedValue = repository.add(addDto.lx)
            if (addedValue != null) {
                call.respond(mapper.map(addedValue))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Light density isn't valid!")
            }
        } catch (e: ContentTransformationException) {
            call.respond(HttpStatusCode.BadRequest, "Light density isn't valid!")
        }

    }

    suspend fun lightSensorValuesWebSocket(session: WebSocketServerSession) {
        repository.flow.collect { values ->
            session.sendSerialized(values.map(mapper::map))
        }
    }
}