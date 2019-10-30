package org.mongeez.shell

import org.mongeez.shell.parameter.MONGEEZ_PARAMETERS
import org.springframework.shell.Availability
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellMethodAvailability

@ShellComponent
class MongeezCommands {

    @ShellMethod("Runs mongeez with given parameters")
    fun run(): String {
        return "Successfully run!"
    }

    @ShellMethod("Dry-runs mongeez with given parameters")
    fun dryRun(): String {
        return "Successfully dry-run!"
    }

    @ShellMethodAvailability
    fun availabilityCheck(): Availability {
        return MONGEEZ_PARAMETERS.run {
            if (changeSetListFileParameter == null) {
                Availability.unavailable("change set list file path is not provided")
            } else {
                if (authenticationEnabled && (userName == null || password == null)) {
                    Availability.unavailable("authentication is enabled but username or password is missing")
                } else {
                    Availability.available()
                }
            }
        }
    }
}