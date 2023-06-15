package tech.antee.junkiot.features.interfaces.light_sensor.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onSubscription
import tech.antee.junkiot.features.controll.data.daos.ControllerDao
import tech.antee.junkiot.features.interfaces.light_sensor.data.daos.LightSensorValuesDao
import tech.antee.junkiot.features.interfaces.light_sensor.data.mappers.LightSensorValueDomainMapper
import tech.antee.junkiot.features.interfaces.light_sensor.domain.models.LightSensorValue
import tech.antee.junkiot.features.interfaces.light_sensor.domain.repositories.LightSensorValueRepository

class LightSensorValueRepositoryImpl(
    private val lightSensorValuesDao: LightSensorValuesDao,
    private val controllerDao: ControllerDao
) : LightSensorValueRepository {

    private val mapper by lazy { LightSensorValueDomainMapper() }

    // TODO: secure multi-threading incidents
    private val _flow = MutableStateFlow<List<LightSensorValue>>(listOf())
    override val flow: Flow<List<LightSensorValue>> = _flow.onSubscription {
        _flow.value = getAll()
    }

    override suspend fun add(
        controllerId: Int,
        lx: Int
    ): LightSensorValue? = controllerDao.get(controllerId)?.let { controller ->
        lightSensorValuesDao.add(lx, controller.id)
            ?.let { mapper.mapToDomain(it) }
            ?.also { _flow.value = getAll() }
    }

    override suspend fun get(id: Int): LightSensorValue? = lightSensorValuesDao.get(id)?.let { mapper.mapToDomain(it) }

    override suspend fun getAll(): List<LightSensorValue> = lightSensorValuesDao
        .getAll()
        .map(mapper::mapToDomain)

    override suspend fun getAll(controllerId: Int): List<LightSensorValue> = lightSensorValuesDao
        .getAll(controllerId)
        .map(mapper::mapToDomain)

    override suspend fun update(model: LightSensorValue): Boolean = lightSensorValuesDao
        .update(mapper.mapToData(model))
        .also { isUpdated -> if (isUpdated) _flow.value = getAll()}

    override suspend fun delete(id: Int): Boolean = lightSensorValuesDao.delete(id)
        .also { isDeleted -> if (isDeleted) _flow.value = getAll()}
}