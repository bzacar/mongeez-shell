package org.mongeez.shell.parameter.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class OutputMessageProviderTest {
    private val outputMessageProvider = OutputMessageProvider()

    @Test
    fun getSetMethodMessage() {
        val actual = outputMessageProvider.getSetMethodMessage(PROPERTY_NAME, VALUE)
        assertThat(actual).isEqualTo("$PROPERTY_NAME is set to '$VALUE'")
    }

    @Test
    fun getEnableMethodMessage() {
        val actual = outputMessageProvider.getEnableMethodMessage(PROPERTY_NAME)
        assertThat(actual).isEqualTo("$PROPERTY_NAME is enabled")
    }

    @Test
    fun getDisableMethodMessage() {
        val actual = outputMessageProvider.getDisableMethodMessage(PROPERTY_NAME)
        assertThat(actual).isEqualTo("$PROPERTY_NAME is disabled")
    }

    private companion object {
        const val PROPERTY_NAME = "<some property name>"
        const val VALUE = "<some value>"
    }
}