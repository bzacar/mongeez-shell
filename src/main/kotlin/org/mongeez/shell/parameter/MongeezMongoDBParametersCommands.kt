package org.mongeez.shell.parameter

import org.mongeez.shell.parameter.util.OutputMessageProvider
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MongeezMongoDBParametersCommands(private val outputMessageProvider: OutputMessageProvider) {

    @ShellMethod("Sets the host name/address of the database server")
    fun setServerHostAddress(hostAddress: String): String {
        MONGEEZ_PARAMETERS.hostAddress = hostAddress
        return outputMessageProvider.getSetMethodMessage(HOST_ADDRESS_PROPERTY_NAME, hostAddress)
    }

    @ShellMethod("Sets the port of the database server")
    fun setServerPort(port: Int): String {
        MONGEEZ_PARAMETERS.port = port
        return outputMessageProvider.getSetMethodMessage(SERVER_PORT_PROPERTY_NAME, port)
    }

    @ShellMethod("Sets the name of the database on which the change sets will be executed")
    fun setDatabaseName(databaseName: String): String {
        MONGEEZ_PARAMETERS.databaseName = databaseName
        return outputMessageProvider.getSetMethodMessage(DATABASE_NAME_PROPERTY_NAME, databaseName)
    }

    internal companion object {
        const val HOST_ADDRESS_PROPERTY_NAME = "Host address"
        const val SERVER_PORT_PROPERTY_NAME = "Server port"
        const val DATABASE_NAME_PROPERTY_NAME = "Database name"
    }
}