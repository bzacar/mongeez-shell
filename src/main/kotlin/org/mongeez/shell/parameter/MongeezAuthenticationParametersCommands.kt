package org.mongeez.shell.parameter

import org.mongeez.shell.parameter.util.OutputMessageProvider
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MongeezAuthenticationParametersCommands(private val outputMessageProvider: OutputMessageProvider) {

    @ShellMethod("Enables authentication")
    fun enableAuthentication(): String {
        MONGEEZ_PARAMETERS.authenticationEnabled = true
        return outputMessageProvider.getEnableMethodMessage("Authentication")
    }

    @ShellMethod("Disables authentication")
    fun disableAuthentication(): String {
        MONGEEZ_PARAMETERS.authenticationEnabled = false
        return outputMessageProvider.getDisableMethodMessage("Authentication")
    }

    @ShellMethod("Set name of the authentication database")
    fun setAuthenticationDatabase(authenticationDatabase: String): String {
        MONGEEZ_PARAMETERS.authenticationDatabase = authenticationDatabase
        return outputMessageProvider.getSetMethodMessage("Authentication database", authenticationDatabase)
    }

    @ShellMethod("Set username for authenticating to the database")
    fun setUsername(username: String): String {
        MONGEEZ_PARAMETERS.userName = username
        return outputMessageProvider.getSetMethodMessage("Username", username)
    }

    @ShellMethod("Set password for authenticating to the database")
    fun setPassword(): String {
        print("Please enter your password: ")
        MONGEEZ_PARAMETERS.password = System.console().readPassword()
        return "Password is set"
    }
}