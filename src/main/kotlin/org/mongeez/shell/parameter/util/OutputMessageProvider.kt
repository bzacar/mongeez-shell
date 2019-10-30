package org.mongeez.shell.parameter.util

import org.springframework.stereotype.Service

@Service
class OutputMessageProvider {
    fun getSetMethodMessage(propertyName: String, value: Any): String {
        return "$propertyName is set to '$value'"
    }

    fun getEnableMethodMessage(propertyName: String): String {
        return "$propertyName is enabled"
    }

    fun getDisableMethodMessage(propertyName: String): String {
        return "$propertyName is disabled"
    }
}