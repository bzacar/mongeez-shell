package org.mongeez.shell

import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MongeezCommands {

    @ShellMethod("A test method to check spring shell functionality")
    fun test(): String {
        return "Hello world"
    }
}