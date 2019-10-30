package org.mongeez.shell

import org.mongeez.shell.parameter.MONGEEZ_PARAMETERS
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MongeezCommands {

    @ShellMethod("Runs mongeez with given parameters")
    fun run(): String {
        return if (MONGEEZ_PARAMETERS.isReady()) {
            "Successfully run!"
        } else {
            "Cannot run due to missing parameters"
        }
    }

    @ShellMethod("Dry-runs mongeez with given parameters")
    fun dryRun(): String {
        return if (MONGEEZ_PARAMETERS.isReady()) {
            "Successfully run!"
        } else {
            "Cannot run due to missing parameters"
        }
    }
}