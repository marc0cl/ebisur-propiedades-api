package org.ebisur

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.slf4j.LoggerFactory

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger(Application::class.java)
    val app = runApplication<Application>(*args)
    logger.info("Application is running with active profiles: ${app.environment.activeProfiles.joinToString()}")
}