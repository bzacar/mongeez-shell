package org.mongeez.shell.parameter

import org.mongeez.shell.parameter.util.OutputMessageProvider
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MongeezMongoDBParametersCommands(private val outputMessageProvider: OutputMessageProvider) {

    @ShellMethod("Sets the host name/address of the database server")
    fun setServerHostAddress(hostAddress: String): String {
        MONGEEZ_PARAMETERS.hostAddress = hostAddress
        return outputMessageProvider.getSetMethodMessage("Host address", hostAddress)
    }

    @ShellMethod("Sets the port of the database server")
    fun setServerPort(port: Int): String {
        MONGEEZ_PARAMETERS.port = port
        return outputMessageProvider.getSetMethodMessage("Server port", port)
    }

    @ShellMethod("Sets the name of the database on which the change sets will be executed")
    fun setDatabaseName(databaseName: String): String {
        MONGEEZ_PARAMETERS.databaseName = databaseName
        return outputMessageProvider.getSetMethodMessage("Database name", databaseName)
    }
}