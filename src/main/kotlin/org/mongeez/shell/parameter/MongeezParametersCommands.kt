package org.mongeez.shell.parameter

import org.mongeez.shell.parameter.util.OutputMessageProvider
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MongeezParametersCommands(private val outputMessageProvider: OutputMessageProvider) {

    @ShellMethod("Sets path of the file that contains descriptions of changeset files")
    fun setChangeSetListFile(path: String): String {
        MONGEEZ_PARAMETERS.changeSetListFileParameter = path
        return outputMessageProvider.getSetMethodMessage("File path", path)
    }

    @ShellMethod("Sets context of the change sets to be run")
    fun setContext(context: String): String {
        MONGEEZ_PARAMETERS.context = context
        return outputMessageProvider.getSetMethodMessage("Context", context)
    }

    @ShellMethod("Enables option to use mongo shell to execute change sets")
    fun enableMongoShell(): String {
        MONGEEZ_PARAMETERS.useMongoShell = true
        return outputMessageProvider.getEnableMethodMessage("Using mongo shell")
    }

    @ShellMethod("Enables option to use 'db.eval' to execute change sets")
    fun enableDbEval(): String {
        MONGEEZ_PARAMETERS.useMongoShell = false
        return outputMessageProvider.getEnableMethodMessage("Using db.eval")
    }

    @ShellMethod("Displays current parameter values")
    fun showParameters(): String {
        return MONGEEZ_PARAMETERS.toString()
    }
}