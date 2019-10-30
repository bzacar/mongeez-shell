package org.mongeez.shell.parameter

import org.mongeez.shell.parameter.util.OutputMessageProvider
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MongeezLogParametersCommands(private val outputMessageProvider: OutputMessageProvider) {

    @ShellMethod("Changes the log level to debug")
    fun enableDebugLogs(): String {
        MONGEEZ_PARAMETERS.debug = true
        return outputMessageProvider.getEnableMethodMessage("Debug logs")
    }

    @ShellMethod("Changes the log level to info")
    fun disableDebugLogs(): String {
        MONGEEZ_PARAMETERS.debug = false
        return outputMessageProvider.getDisableMethodMessage("Debug logs")
    }

    @ShellMethod("Adds a console log appender")
    fun enableConsoleLogs(): String {
        MONGEEZ_PARAMETERS.logConsole = true
        return outputMessageProvider.getEnableMethodMessage("Console logs")
    }

    @ShellMethod("Removes the console log appender")
    fun disableConsoleLogs(): String {
        MONGEEZ_PARAMETERS.logConsole = false
        return outputMessageProvider.getDisableMethodMessage("Console logs")
    }
}