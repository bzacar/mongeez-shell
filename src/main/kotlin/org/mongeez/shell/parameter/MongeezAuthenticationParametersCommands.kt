package org.mongeez.shell.parameter

import org.mongeez.shell.parameter.util.OutputMessageProvider
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MongeezAuthenticationParametersCommands(private val outputMessageProvider: OutputMessageProvider) {

    @ShellMethod("Enables authentication")
    fun enableAuthentication(): String {
        MONGEEZ_PARAMETERS.authenticationEnabled = true
        return outputMessageProvider.getEnableMethodMessage(AUTHENTICATION_PROPERTY_NAME)
    }

    @ShellMethod("Disables authentication")
    fun disableAuthentication(): String {
        MONGEEZ_PARAMETERS.authenticationEnabled = false
        return outputMessageProvider.getDisableMethodMessage(AUTHENTICATION_PROPERTY_NAME)
    }

    @ShellMethod("Set name of the authentication database")
    fun setAuthenticationDatabase(authenticationDatabase: String): String {
        MONGEEZ_PARAMETERS.authenticationDatabase = authenticationDatabase
        return outputMessageProvider.getSetMethodMessage(AUTHENTICATION_DATABASE_PROPERTY_NAME, authenticationDatabase)
    }

    @ShellMethod("Set username for authenticating to the database")
    fun setUsername(username: String): String {
        MONGEEZ_PARAMETERS.username = username
        return outputMessageProvider.getSetMethodMessage(USERNAME_PROPERTY_NAME, username)
    }

    @ShellMethod("Set password for authenticating to the database")
    fun setPassword(): String {
        print("Please enter your password: ")
        MONGEEZ_PARAMETERS.password = System.console().readPassword()
        return "Password is set"
    }

    internal companion object {
        const val AUTHENTICATION_PROPERTY_NAME = "Authentication"
        const val AUTHENTICATION_DATABASE_PROPERTY_NAME = "Authentication database"
        const val USERNAME_PROPERTY_NAME = "Username"
    }
}