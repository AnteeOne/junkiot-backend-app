package tech.antee.junkiot.plugins.ktx

import io.ktor.server.application.*

fun Application.getConfigString(path: String): String = config().property(path).getString()

fun Application.getConfigInt(path: String): Int = config().property(path).getString().toInt()

fun Application.config() = environment.config