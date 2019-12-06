package org.mongeez.shell

import mu.KLogger
import org.mongeez.shell.parameter.MONGEEZ_PARAMETERS
import org.mongeez.shell.parameter.util.MongeezFactory
import org.springframework.shell.Availability
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellMethodAvailability
import java.lang.RuntimeException

@ShellComponent
class MongeezCommands(private val mongeezFactory: MongeezFactory,
                      private val logger: KLogger) {

    @ShellMethod("Runs mongeez with given parameters")
    fun run(): String {
        return try {
            mongeezFactory.create().process()
            "Run completed!"
        } catch (ex: RuntimeException) {
            logger.error { ex.message }
            "Run have failed!"
        }
    }

    @ShellMethod("Dry-runs mongeez with given parameters")
    fun dryRun(): String {
        return try {
            val stringBuilder = StringBuilder()
            mongeezFactory.create().dryRun().print { stringBuilder.appendln(it) }
            stringBuilder.toString()
        } catch (ex: RuntimeException) {
            logger.error { ex.message }
            "Dry-run have failed!"
        }
    }

    @ShellMethodAvailability
    fun availabilityCheck(): Availability {
        return MONGEEZ_PARAMETERS.run {
            if (changeSetListFileParameter == null) {
                Availability.unavailable("change set list file path is not provided")
            } else {
                if (authenticationEnabled && (username == null || password == null)) {
                    Availability.unavailable("authentication is enabled but username or password is missing")
                } else {
                    Availability.available()
                }
            }
        }
    }
}