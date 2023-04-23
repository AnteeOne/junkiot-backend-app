package tech.antee.junkiot.features.controll.data.daos

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import tech.antee.junkiot.features.controll.data.entities.ControllerEntity
import tech.antee.junkiot.features.controll.data.tables.ControllersTable
import tech.antee.junkiot.plugins.db.dbQuery

class ControllerDaoImpl : ControllerDao {
    override suspend fun add(typeId: Int, controllerName: String): ControllerEntity? = with(ControllersTable) {
        dbQuery {
            val insertStatement = insert { table ->
                table[controllerType] = typeId
                table[name] = controllerName
                table[isOnline] = false
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::mapRowToEntity)
        }
    }

    override suspend fun get(entityId: Int): ControllerEntity? = with(ControllersTable) {
        dbQuery {
            select { id eq entityId }
                .map(::mapRowToEntity)
                .singleOrNull()
        }
    }

    override suspend fun getAll(): List<ControllerEntity> = with(ControllersTable) {
        dbQuery { selectAll().orderBy(id).toList().map(::mapRowToEntity) }
    }

    override suspend fun update(entity: ControllerEntity): Boolean = with(ControllersTable) {
        dbQuery {
            update(
                where = { id eq entity.id },
                body = { table ->
                    table[controllerType] = entity.controllerType
                    table[name] = entity.name
                    table[isOnline] = entity.isOnline
                },
            ) > 0
        }
    }

    override suspend fun updateOnline(controllerId: Int, isOnline: Boolean): Boolean = with(ControllersTable) {
        dbQuery {
            update(
                where = { id eq controllerId },
                body = { table ->
                    table[ControllersTable.isOnline] = isOnline
                },
            ) > 0
        }
    }

    override suspend fun delete(entityId: Int): Boolean = with(ControllersTable) {
        dbQuery { deleteWhere { id eq entityId } > 0 }
    }

    private fun mapRowToEntity(row: ResultRow) = with(ControllersTable) {
        ControllerEntity(
            id = row[id],
            controllerType = row[controllerType],
            name = row[name],
            isOnline = row[isOnline]
        )
    }
}