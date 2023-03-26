package tech.antee.junkiot.features.light_sensor.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onSubscription
import tech.antee.junkiot.features.light_sensor.data.daos.LightSensorValuesDao
import tech.antee.junkiot.features.light_sensor.data.mappers.LightSensorValueDomainMapper
import tech.antee.junkiot.features.light_sensor.domain.models.LightSensorValue
import tech.antee.junkiot.features.light_sensor.domain.repository.LightSensorValueRepository

class LightSensorValueRepositoryImpl(
    private val dao: LightSensorValuesDao
) : LightSensorValueRepository {

    private val mapper by lazy { LightSensorValueDomainMapper() }

    // TODO: secure multi-threading incidents
    private val _flow = MutableStateFlow<List<LightSensorValue>>(listOf())
    override val flow: Flow<List<LightSensorValue>> = _flow.onSubscription {
        _flow.value = getAll()
    }

    override suspend fun add(lx: Int): LightSensorValue? = dao.add(lx)
        ?.let { mapper.mapToDomain(it) }
        ?.also { _flow.value = getAll() }

    override suspend fun get(id: Int): LightSensorValue? = dao.get(id)?.let { mapper.mapToDomain(it) }

    override suspend fun getAll(): List<LightSensorValue> = dao.getAll().map(mapper::mapToDomain)

    override suspend fun update(model: LightSensorValue): Boolean = dao.update(mapper.mapToData(model))
        .also { isUpdated -> if (isUpdated) _flow.value = getAll()}

    override suspend fun delete(id: Int): Boolean = dao.delete(id)
        .also { isDeleted -> if (isDeleted) _flow.value = getAll()}
}