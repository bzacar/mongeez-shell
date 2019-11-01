package org.mongeez.shell.parameter

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender
import ch.qos.logback.core.ConsoleAppender
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mongeez.shell.parameter.MongeezLogParametersCommands.Companion.CONSOLE_LOGS_PROPERTY_NAME
import org.mongeez.shell.parameter.MongeezLogParametersCommands.Companion.DEBUG_LOGS_PROPERTY_NAME
import org.mongeez.shell.parameter.util.OutputMessageProvider

internal class MongeezLogParametersCommandsTest {
    private val rootLoggerMock: ch.qos.logback.classic.Logger = mockk {
        every { detachAppender(any<Appender<ILoggingEvent>>()) } returns true
    }
    private val consoleLogAppenderMock: ConsoleAppender<ILoggingEvent> = mockk()
    private val outputMessageProviderMock: OutputMessageProvider = mockk {
        every { getEnableMethodMessage(DEBUG_LOGS_PROPERTY_NAME) } returns DEBUG_LOGS_ARE_ENABLED_MESSAGE
        every { getDisableMethodMessage(DEBUG_LOGS_PROPERTY_NAME) } returns DEBUG_LOGS_ARE_DISABLED_MESSAGE
        every { getEnableMethodMessage(CONSOLE_LOGS_PROPERTY_NAME) } returns CONSOLE_LOGS_ARE_ENABLED_MESSAGE
        every { getDisableMethodMessage(CONSOLE_LOGS_PROPERTY_NAME) } returns CONSOLE_LOGS_ARE_DISABLED_MESSAGE
    }
    private val testSubject = MongeezLogParametersCommands(rootLoggerMock, consoleLogAppenderMock, outputMessageProviderMock)

    @Test
    fun enableDebugLogs() {
        val actual = testSubject.enableDebugLogs()
        assertThat(actual).isEqualTo(DEBUG_LOGS_ARE_ENABLED_MESSAGE)
        verify { rootLoggerMock.level = Level.DEBUG }
    }

    @Test
    fun disableDebugLogs() {
        val actual = testSubject.disableDebugLogs()
        assertThat(actual).isEqualTo(DEBUG_LOGS_ARE_DISABLED_MESSAGE)
        verify { rootLoggerMock.level = Level.INFO }
    }

    @Test
    fun enableConsoleLogs() {
        val actual = testSubject.enableConsoleLogs()
        assertThat(actual).isEqualTo(CONSOLE_LOGS_ARE_ENABLED_MESSAGE)
        verify { rootLoggerMock.addAppender(consoleLogAppenderMock) }
    }

    @Test
    fun disableConsoleLogs() {
        val actual = testSubject.disableConsoleLogs()
        assertThat(actual).isEqualTo(CONSOLE_LOGS_ARE_DISABLED_MESSAGE)
        verify { rootLoggerMock.detachAppender(consoleLogAppenderMock) }
    }

    private companion object {
        const val DEBUG_LOGS_ARE_ENABLED_MESSAGE = "DEBUG_LOGS_ARE_ENABLED_MESSAGE"
        const val DEBUG_LOGS_ARE_DISABLED_MESSAGE = "DEBUG_LOGS_ARE_DISABLED_MESSAGE"
        const val CONSOLE_LOGS_ARE_ENABLED_MESSAGE = "CONSOLE_LOGS_ARE_ENABLED_MESSAGE"
        const val CONSOLE_LOGS_ARE_DISABLED_MESSAGE = "CONSOLE_LOGS_ARE_DISABLED_MESSAGE"
    }
}