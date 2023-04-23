package tech.antee.junkiot.features.light_sensor.api.controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.websocket.*
import tech.antee.junkiot.features.di.AppComponent
import tech.antee.junkiot.features.light_sensor.api.dtos.AddLightSensorValueDto
import tech.antee.junkiot.features.light_sensor.api.mappers.LightSensorDataMapper
import tech.antee.junkiot.features.light_sensor.data.daos.LightSensorValuesDaoImpl
import tech.antee.junkiot.features.light_sensor.data.repositories.LightSensorValueRepositoryImpl
import tech.antee.junkiot.features.light_sensor.di.LightSensorComponent

class LightSensorControllerImpl {

    private val mapper: LightSensorDataMapper by lazy { LightSensorDataMapper() }

    private val repository by lazy {
        LightSensorValueRepositoryImpl(
            lightSensorValuesDao = LightSensorValuesDaoImpl(),
            controllerDao = AppComponent.controllerDao
        )
    }

    suspend fun getLightSensorValues(call: ApplicationCall) {
        val controllerId = call.request.queryParameters["controllerId"]?.toInt()
        val values = if (controllerId != null) {
            repository.getAll(controllerId).map(mapper::map)
        } else {
            repository.getAll().map(mapper::map)
        }
        call.respond(values)
    }

    suspend fun addLightSensorValue(call: ApplicationCall) {
        try {
            val addDto = call.receive<AddLightSensorValueDto>()
            val addedValue = repository.add(
                lx = addDto.lx,
                controllerId = addDto.controllerId
            )
            if (addedValue != null) {
                LightSensorComponent.lightSensorPredictionService.predict(addedValue)
                call.respond(mapper.map(addedValue))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Light sensor value isn't valid!")
            }
        } catch (e: ContentTransformationException) {
            call.respond(HttpStatusCode.BadRequest, "Light sensor value isn't valid!")
        }

    }

    suspend fun lightSensorValuesWebSocket(session: WebSocketServerSession) {
        repository.flow.collect { values ->
            session.sendSerialized(values.map(mapper::map))
        }
    }

    suspend fun lightSensorPredictionsValuesWebSocket(session: WebSocketServerSession) {
        LightSensorComponent.lightSensorPredictionService.predictions.collect { values ->
            session.sendSerialized(values.map(mapper::map))
        }
    }
}