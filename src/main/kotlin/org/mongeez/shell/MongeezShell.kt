package org.mongeez.shell

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MongeezShell

fun main(args: Array<String>) {
    runApplication<MongeezShell>(*args)
}