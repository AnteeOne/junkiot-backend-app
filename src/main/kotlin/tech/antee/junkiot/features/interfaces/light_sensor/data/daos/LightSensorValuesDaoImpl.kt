package tech.antee.junkiot.features.interfaces.light_sensor.data.daos

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import tech.antee.junkiot.features.interfaces.light_sensor.data.entities.LightSensorValueEntity
import tech.antee.junkiot.features.interfaces.light_sensor.data.tables.LightSensorValuesTable
import tech.antee.junkiot.plugins.db.dbQuery

class LightSensorValuesDaoImpl : LightSensorValuesDao {

    override suspend fun add(lxValue: Int, controllerEntityId: Int): LightSensorValueEntity? =
        with(LightSensorValuesTable) {
            dbQuery {
                val insertStatement = insert { table ->
                    table[lx] = lxValue
                    table[controllerId] = controllerEntityId
                }
                insertStatement.resultedValues?.singleOrNull()?.let(::mapRowToEntity)
            }
        }

    override suspend fun get(id: Int): LightSensorValueEntity? = with(LightSensorValuesTable) {
        dbQuery {
            select { LightSensorValuesTable.id eq id }
                .map(::mapRowToEntity)
                .singleOrNull()
        }
    }

    override suspend fun getAll(): List<LightSensorValueEntity> = with(LightSensorValuesTable) {
        dbQuery { selectAll().orderBy(id).toList().map(::mapRowToEntity) }
    }

    override suspend fun getAll(controllerId: Int): List<LightSensorValueEntity> = with(LightSensorValuesTable) {
        dbQuery {
            select { LightSensorValuesTable.controllerId eq controllerId }
                .orderBy(id)
                .toList()
                .map(::mapRowToEntity)
        }
    }

    override suspend fun update(entity: LightSensorValueEntity): Boolean = with(LightSensorValuesTable) {
        dbQuery {
            update(
                where = { id eq entity.id },
                body = { table ->
                    table[lx] = entity.lx
                    table[controllerId] = entity.controllerId
                },
            ) > 0
        }
    }

    override suspend fun delete(id: Int): Boolean = with(LightSensorValuesTable) {
        dbQuery { deleteWhere { LightSensorValuesTable.id eq id } > 0 }
    }

    private fun mapRowToEntity(row: ResultRow) = with(LightSensorValuesTable) {
        LightSensorValueEntity(
            id = row[id],
            lx = row[lx],
            controllerId = row[controllerId]
        )
    }
}