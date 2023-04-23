package tech.antee.junkiot.features.di

import tech.antee.junkiot.features.controll.data.daos.ControllerDao
import tech.antee.junkiot.features.controll.data.daos.ControllerDaoImpl

object AppComponent {

    val controllerDao: ControllerDao by lazy {
        ControllerDaoImpl()
    }
}