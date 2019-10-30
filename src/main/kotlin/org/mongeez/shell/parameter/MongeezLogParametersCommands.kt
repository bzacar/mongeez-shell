package org.mongeez.shell.parameter

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import org.mongeez.shell.parameter.util.OutputMessageProvider
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MongeezLogParametersCommands(private val rootLogger: ch.qos.logback.classic.Logger,
                                   private val consoleLogAppender: ConsoleAppender<ILoggingEvent>,
                                   private val outputMessageProvider: OutputMessageProvider) {

    @ShellMethod("Changes the log level to debug")
    fun enableDebugLogs(): String {
        rootLogger.level = Level.DEBUG
        return outputMessageProvider.getEnableMethodMessage("Debug logs")
    }

    @ShellMethod("Changes the log level to info")
    fun disableDebugLogs(): String {
        rootLogger.level = Level.INFO
        return outputMessageProvider.getDisableMethodMessage("Debug logs")
    }

    @ShellMethod("Adds a console log appender")
    fun enableConsoleLogs(): String {
        rootLogger.addAppender(consoleLogAppender)
        return outputMessageProvider.getEnableMethodMessage("Console logs")
    }

    @ShellMethod("Removes the console log appender")
    fun disableConsoleLogs(): String {
        rootLogger.detachAppender(consoleLogAppender)
        return outputMessageProvider.getDisableMethodMessage("Console logs")
    }
}