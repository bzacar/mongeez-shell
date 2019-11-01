package org.mongeez.shell.parameter

import org.mongeez.shell.parameter.util.OutputMessageProvider
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MongeezParametersCommands(private val outputMessageProvider: OutputMessageProvider) {

    @ShellMethod("Sets path of the file that contains descriptions of changeset files")
    fun setChangeSetListFile(path: String): String {
        MONGEEZ_PARAMETERS.changeSetListFileParameter = path
        return outputMessageProvider.getSetMethodMessage(FILE_PATH_PROPERTY_NAME, path)
    }

    @ShellMethod("Sets context of the change sets to be run")
    fun setContext(context: String): String {
        MONGEEZ_PARAMETERS.context = context
        return outputMessageProvider.getSetMethodMessage(CONTEXT_PROPERTY_NAME, context)
    }

    @ShellMethod("Enables option to use mongo shell to execute change sets")
    fun enableMongoShell(): String {
        MONGEEZ_PARAMETERS.useMongoShell = true
        return outputMessageProvider.getEnableMethodMessage(USING_MONGO_SHELL_PROPERTY_NAME)
    }

    @ShellMethod("Enables option to use 'db.eval' to execute change sets")
    fun enableDbEval(): String {
        MONGEEZ_PARAMETERS.useMongoShell = false
        return outputMessageProvider.getEnableMethodMessage(USING_DB_EVAL_PROPERTY_NAME)
    }

    @ShellMethod("Displays current parameter values")
    fun showParameters(): String {
        return MONGEEZ_PARAMETERS.toString()
    }

    internal companion object {
        const val FILE_PATH_PROPERTY_NAME = "File path"
        const val CONTEXT_PROPERTY_NAME = "Context"
        const val USING_MONGO_SHELL_PROPERTY_NAME = "Using mongo shell"
        const val USING_DB_EVAL_PROPERTY_NAME = "Using db.eval"
    }
}