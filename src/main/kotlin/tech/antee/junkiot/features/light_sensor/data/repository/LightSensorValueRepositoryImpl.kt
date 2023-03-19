package tech.antee.junkiot.features.light_sensor.data.repository

import tech.antee.junkiot.features.light_sensor.data.daos.LightSensorValuesDao
import tech.antee.junkiot.features.light_sensor.data.mappers.LightSensorValueDomainMapper
import tech.antee.junkiot.features.light_sensor.domain.models.LightSensorValue
import tech.antee.junkiot.features.light_sensor.domain.repository.LightSensorValueRepository

class LightSensorValueRepositoryImpl(
    private val dao: LightSensorValuesDao
) : LightSensorValueRepository {

    private val mapper by lazy { LightSensorValueDomainMapper() }

    override suspend fun add(lx: Int): LightSensorValue? = dao.add(lx)?.let { mapper.mapToDomain(it) }

    override suspend fun get(id: Int): LightSensorValue? = dao.get(id)?.let { mapper.mapToDomain(it) }

    override suspend fun getAll(): List<LightSensorValue> = dao.getAll().map(mapper::mapToDomain)

    override suspend fun update(model: LightSensorValue): Boolean = dao.update(mapper.mapToData(model))

    override suspend fun delete(id: Int): Boolean = dao.delete(id)
}