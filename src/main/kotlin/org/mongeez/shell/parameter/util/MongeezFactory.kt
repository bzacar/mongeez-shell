package org.mongeez.shell.parameter.util

import com.mongodb.ServerAddress
import mu.KLogger
import org.mongeez.Mongeez
import org.mongeez.shell.parameter.MONGEEZ_PARAMETERS
import org.springframework.beans.factory.ObjectFactory
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component

@Component
class MongeezFactory(private val mongeezObjectFactory: ObjectFactory<Mongeez>,
                     private val logger: KLogger) {

    fun create(): Mongeez {
        return try {
            mongeezObjectFactory.getObject().apply {
                with(MONGEEZ_PARAMETERS) {
                    setFile(FileSystemResource(changeSetListFile))
                    setServerAddress(ServerAddress(hostAddress, port))
                    setDbName(databaseName)
                    setUseMongoShell(useMongoShell)
                    mongoAuth?.also { setAuth(it) }
                    context?.also { setContext(it) }
                }
            }
        } catch (ex: RuntimeException) {
            logger.error { ex.message }
            throw ex
        }
    }
}