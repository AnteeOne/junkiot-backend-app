package tech.antee.junkiot.features.interfaces.claps_detector.api.controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.launch
import tech.antee.junkiot.features.controll.domain.repositories.ControllerRepository
import tech.antee.junkiot.features.di.AppComponent
import tech.antee.junkiot.features.integrations.home_assistant.homeAssistantService
import tech.antee.junkiot.features.interfaces.claps_detector.api.dtos.AddClapDetectionValueDto
import tech.antee.junkiot.features.interfaces.claps_detector.api.mappers.ClapsDetectorDataMapper
import tech.antee.junkiot.features.interfaces.claps_detector.data.daos.ClapsDetectionValuesDaoImpl
import tech.antee.junkiot.features.interfaces.claps_detector.data.repositories.ClapsDetectionValueRepositoryImpl

class ClapsDetectorControllerImpl {

    private val mapper: ClapsDetectorDataMapper by lazy { ClapsDetectorDataMapper() }

    private val clapsDetectionValuesRepository by lazy {
        ClapsDetectionValueRepositoryImpl(
            clapsDetectionValuesDao = ClapsDetectionValuesDaoImpl(),
            controllerDao = AppComponent.controllerDao
        )
    }

    private val controllerRepository: ControllerRepository by lazy { AppComponent.controllerRepository }

    suspend fun getValues(call: ApplicationCall) {
        val controllerId = call.request.queryParameters["controllerId"]?.toInt()
        val values = if (controllerId != null) {
            clapsDetectionValuesRepository.getAll(controllerId).map(mapper::map)
        } else {
            clapsDetectionValuesRepository.getAll().map(mapper::map)
        }
        call.respond(values)
    }

    suspend fun addValue(call: ApplicationCall) {
        try {
            val addDto = call.receive<AddClapDetectionValueDto>()
            val addedValue = clapsDetectionValuesRepository.add(
                controllerId = addDto.controllerId
            )
            if (addedValue != null) {
                homeAssistantService.toggleInterfaces()
                call.respond(mapper.map(addedValue))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Value isn't valid!")
            }
        } catch (e: ContentTransformationException) {
            call.respond(HttpStatusCode.BadRequest, "Value isn't valid!")
        }

    }

    suspend fun valuesWebSocket(session: WebSocketServerSession) {
        var job: Job? = null
        var controllerId: Int? = null
        try {
            for (frame in session.incoming) {
                val receivedText: String? = (frame as? Frame.Text)?.readText()
                val parsedControllerId = receivedText?.toIntOrNull()
                when {
                    frame is Frame.Close || parsedControllerId == -1 -> {
                        session.close(CloseReason(CloseReason.Codes.NORMAL, "Client said bye!"))
                        controllerId?.let { id -> controllerRepository.updateOnline(id, false) }
                        job?.cancel()
                        job = null
                        controllerId = null
                    }
                    parsedControllerId == null || job != null -> continue
                    else -> {
                        job = session.launch {
                            clapsDetectionValuesRepository
                                .observeValues(parsedControllerId)
                                .collect { values ->
                                    // TODO: remove hardcode value
                                    session.sendSerialized(values.map(mapper::map).takeLast(10))
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