package tech.antee.junkiot.features.di

import tech.antee.junkiot.features.controll.data.daos.ControllerDao
import tech.antee.junkiot.features.controll.data.daos.ControllerDaoImpl
import tech.antee.junkiot.features.controll.data.repositories.ControllerRepositoryImpl
import tech.antee.junkiot.features.controll.domain.repositories.ControllerRepository

object AppComponent {

    val controllerRepository: ControllerRepository by lazy {
        ControllerRepositoryImpl(
            controllerDao
        )
    }

    val controllerDao: ControllerDao by lazy {
        ControllerDaoImpl()
    }
}