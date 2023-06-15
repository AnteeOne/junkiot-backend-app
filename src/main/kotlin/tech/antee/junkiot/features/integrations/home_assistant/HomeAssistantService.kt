package tech.antee.junkiot.features.integrations.home_assistant

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.client.plugins.contentnegotiation.*
import tech.antee.junkiot.features.integrations.home_assistant.dto.InterfacesListDto

lateinit var homeAssistantService: HomeAssistantService

class HomeAssistantService(
    private val client: HttpClient
) {

    suspend fun toggleInterfaces(
        interfaces: List<String> = listOf("light.living_room_desk_lamp", "light.living_room_desk_lamp_ambilight"),
    ) {
        val url = "http://192.168.1.65:8123/api/services/light/toggle"
        val response: HttpResponse = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(InterfacesListDto(interfaces))
            header("Authorization", "Bearer kek")
        }
    }

    suspend fun toggleAlarm(
        interfaces: List<String> = listOf("input_boolean.alarm"),
    ) {
        val url = "http://192.168.1.65:8123/api/services/homeassistant/toggle"
        val response: HttpResponse = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(InterfacesListDto(interfaces))
            header("Authorization", "Bearer kek")
        }
    }

    suspend fun toggleInterfaces(
        interfaces: List<String> = listOf("light.living_room_desk_lamp", "light.living_room_desk_lamp_ambilight"),
//        interfaces: List<String> = listOf("light.bedroom_light"),
        isNight: Boolean
    ) {
        if (isNight) turnOnInterfaces(interfaces) else turnOffInterfaces(interfaces)
    }

    suspend fun turnOnInterfaces(
        interfaces: List<String>
    ) {
        val url = "http://192.168.1.65:8123/api/services/light/turn_on"
        val response: HttpResponse = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(InterfacesListDto(interfaces))
            header("Authorization", "Bearer kek")
        }
    }

    suspend fun turnOffInterfaces(
        interfaces: List<String>
    ) {
        val url = "http://192.168.1.65:8123/api/services/light/turn_off"
        val response: HttpResponse = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(InterfacesListDto(interfaces))
            header("Authorization", "Bearer kek")
        }
    }
}

fun Application.installHomeAssistantService() {
    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    homeAssistantService = HomeAssistantService(httpClient)
}