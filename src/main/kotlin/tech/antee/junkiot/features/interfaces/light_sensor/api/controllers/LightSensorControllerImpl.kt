package tech.antee.junkiot.features.interfaces.light_sensor.api.controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import tech.antee.junkiot.features.controll.domain.repositories.ControllerRepository
import tech.antee.junkiot.features.di.AppComponent
import tech.antee.junkiot.features.integrations.home_assistant.homeAssistantService
import tech.antee.junkiot.features.interfaces.light_sensor.api.dtos.AddLightSensorValueDto
import tech.antee.junkiot.features.interfaces.light_sensor.api.mappers.LightSensorDataMapper
import tech.antee.junkiot.features.interfaces.light_sensor.data.daos.LightSensorValuesDaoImpl
import tech.antee.junkiot.features.interfaces.light_sensor.data.repositories.LightSensorValueRepositoryImpl
import tech.antee.junkiot.features.interfaces.light_sensor.di.LightSensorComponent
import tech.antee.junkiot.features.interfaces.light_sensor.domain.models.LightSensorPredictionValue

class LightSensorControllerImpl {

    private val mapper: LightSensorDataMapper by lazy { LightSensorDataMapper() }

    private val lightSensorValueRepository by lazy {
        LightSensorValueRepositoryImpl(
            lightSensorValuesDao = LightSensorValuesDaoImpl(),
            controllerDao = AppComponent.controllerDao
        )
    }

    private val controllerRepository: ControllerRepository by lazy { AppComponent.controllerRepository }

    suspend fun getLightSensorValues(call: ApplicationCall) {
        val controllerId = call.request.queryParameters["controllerId"]?.toInt()
        val values = if (controllerId != null) {
            lightSensorValueRepository.getAll(controllerId).map(mapper::map)
        } else {
            lightSensorValueRepository.getAll().map(mapper::map)
        }
        call.respond(values)
    }

    suspend fun addLightSensorValue(call: ApplicationCall) {
        try {
            val addDto = call.receive<AddLightSensorValueDto>()
            val addedValue = lightSensorValueRepository.add(
                lx = addDto.lx,
                controllerId = addDto.controllerId
            )
            if (addedValue != null) {
                val prediction = LightSensorComponent.lightSensorPredictionService.predict(addedValue)
                homeAssistantService.toggleInterfaces(isNight = prediction.lightSensorPredictionValue == LightSensorPredictionValue.NIGHT)
                call.respond(mapper.map(addedValue))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Light sensor value isn't valid!")
            }
        } catch (e: ContentTransformationException) {
            call.respond(HttpStatusCode.BadRequest, "Light sensor value isn't valid!")
        }

    }

    suspend fun lightSensorValuesWebSocket(session: WebSocketServerSession) {
        lightSensorValueRepository.flow.collect { values ->
            session.sendSerialized(values.map(mapper::map))
        }
    }

    suspend fun lightSensorPredictionsValuesWebSocket(session: WebSocketServerSession) {
        var job: Job? = null
        var controllerId: Int? = null
        try {
            for (frame in session.incoming) {
                val receivedText: String? = (frame as? Frame.Text)?.readText()
                val parsedControllerId = receivedText?.toIntOrNull()
                when {
                     frame is Frame.Close || parsedControllerId == -1-> {
                        session.close(CloseReason(CloseReason.Codes.NORMAL, "Client said bye!"))
                        controllerId?.let { id -> controllerRepository.updateOnline(id, false) }
                        job?.cancel()
                        job = null
                        controllerId = null
                    }
                    parsedControllerId == null || job != null -> continue
                    else -> {
                        job = session.launch {
                            LightSensorComponent
                                .lightSensorPredictionService
                                .predictions
                                .map { list -> list.filter { prediction -> prediction.controllerId == parsedControllerId } }
                                .collect { values ->
                                    session.sendSerialized(values.takeLast(1).map(mapper::map))
                                }
                        }
                        controllerId = parsedControllerId
                        controllerRepository.updateOnline(controllerId, true)
                    }
                }
            }
            controllerId?.let { id -> controllerRepository.updateOnline(id, false) }
        } catch (e: ClosedReceiveChannelException) {
            controllerId?.let { id -> controllerRepository.updateOnline(id, false) }
            job?.cancel()
            job = null
            controllerId = null
        }
    }
}