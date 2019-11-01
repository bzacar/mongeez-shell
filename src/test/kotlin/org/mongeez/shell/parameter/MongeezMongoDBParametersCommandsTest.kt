package org.mongeez.shell.parameter

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mongeez.shell.parameter.MongeezMongoDBParametersCommands.Companion.DATABASE_NAME_PROPERTY_NAME
import org.mongeez.shell.parameter.MongeezMongoDBParametersCommands.Companion.HOST_ADDRESS_PROPERTY_NAME
import org.mongeez.shell.parameter.MongeezMongoDBParametersCommands.Companion.SERVER_PORT_PROPERTY_NAME
import org.mongeez.shell.parameter.util.OutputMessageProvider

internal class MongeezMongoDBParametersCommandsTest {
    private val outputMessageProviderMock: OutputMessageProvider = mockk {
        every { getSetMethodMessage(HOST_ADDRESS_PROPERTY_NAME, SOME_HOST_ADDRESS) } returns SERVER_HOST_ADDRESS_IS_SET_MESSAGE
        every { getSetMethodMessage(SERVER_PORT_PROPERTY_NAME, SOME_SERVER_PORT) } returns SERVER_PORT_IS_SET_MESSAGE
        every { getSetMethodMessage(DATABASE_NAME_PROPERTY_NAME, SOME_DATABASE_NAME) } returns DATABASE_NAME_IS_SET_MESSAGE
    }
    private val testSubject = MongeezMongoDBParametersCommands(outputMessageProviderMock)

    @Test
    fun setServerHostAddress() {
        val actual = testSubject.setServerHostAddress(SOME_HOST_ADDRESS)
        assertThat(MONGEEZ_PARAMETERS.hostAddress).isEqualTo(SOME_HOST_ADDRESS)
        assertThat(actual).isEqualTo(SERVER_HOST_ADDRESS_IS_SET_MESSAGE)
    }

    @Test
    fun setServerPort() {
        val actual = testSubject.setServerPort(SOME_SERVER_PORT)
        assertThat(MONGEEZ_PARAMETERS.port).isEqualTo(SOME_SERVER_PORT)
        assertThat(actual).isEqualTo(SERVER_PORT_IS_SET_MESSAGE)
    }

    @Test
    fun setDatabaseName() {
        val actual = testSubject.setDatabaseName(SOME_DATABASE_NAME)
        assertThat(MONGEEZ_PARAMETERS.databaseName).isEqualTo(SOME_DATABASE_NAME)
        assertThat(actual).isEqualTo(DATABASE_NAME_IS_SET_MESSAGE)
    }

    private companion object {
        const val SOME_HOST_ADDRESS = "<some host address>"
        const val SOME_SERVER_PORT = 13
        const val SOME_DATABASE_NAME = "<some database name>"
        const val SERVER_HOST_ADDRESS_IS_SET_MESSAGE = "SERVER_HOST_ADDRESS_IS_SET_MESSAGE"
        const val SERVER_PORT_IS_SET_MESSAGE = "SERVER_PORT_IS_SET_MESSAGE"
        const val DATABASE_NAME_IS_SET_MESSAGE = "DATABASE_NAME_IS_SET_MESSAGE"
    }
}