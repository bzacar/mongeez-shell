package org.mongeez.shell.parameter

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mongeez.shell.parameter.MongeezAuthenticationParametersCommands.Companion.AUTHENTICATION_DATABASE_PROPERTY_NAME
import org.mongeez.shell.parameter.MongeezAuthenticationParametersCommands.Companion.AUTHENTICATION_PROPERTY_NAME
import org.mongeez.shell.parameter.MongeezAuthenticationParametersCommands.Companion.USERNAME_PROPERTY_NAME

import org.mongeez.shell.parameter.util.OutputMessageProvider

internal class MongeezAuthenticationParametersCommandsTest {
    private val outputMessageProviderMock: OutputMessageProvider = mockk {
        every { getEnableMethodMessage(AUTHENTICATION_PROPERTY_NAME) } returns AUTHENTICATION_IS_ENABLED_MESSAGE
        every { getDisableMethodMessage(AUTHENTICATION_PROPERTY_NAME) } returns AUTHENTICATION_IS_DISABLED_MESSAGE
        every { getSetMethodMessage(AUTHENTICATION_DATABASE_PROPERTY_NAME, SOME_AUTHENTICATION_DATABASE) } returns AUTHENTICATION_DATABASE_IS_SET_MESSAGE
        every { getSetMethodMessage(USERNAME_PROPERTY_NAME, SOME_USERNAME) } returns USERNAME_IS_SET_MESSAGE
    }
    private val testSubject = MongeezAuthenticationParametersCommands(outputMessageProviderMock)

    @Test
    fun enableAuthentication() {
        val actual = testSubject.enableAuthentication()
        assertThat(MONGEEZ_PARAMETERS.authenticationEnabled).isTrue()
        assertThat(actual).isEqualTo(AUTHENTICATION_IS_ENABLED_MESSAGE)
    }

    @Test
    fun disableAuthentication() {
        val actual = testSubject.disableAuthentication()
        assertThat(MONGEEZ_PARAMETERS.authenticationEnabled).isFalse()
        assertThat(actual).isEqualTo(AUTHENTICATION_IS_DISABLED_MESSAGE)
    }

    @Test
    fun setAuthenticationDatabase() {
        val actual = testSubject.setAuthenticationDatabase(SOME_AUTHENTICATION_DATABASE)
        assertThat(MONGEEZ_PARAMETERS.authenticationDatabase).isEqualTo(SOME_AUTHENTICATION_DATABASE)
        assertThat(actual).isEqualTo(AUTHENTICATION_DATABASE_IS_SET_MESSAGE)
    }

    @Test
    fun setUsername() {
        val actual = testSubject.setUsername(SOME_USERNAME)
        assertThat(MONGEEZ_PARAMETERS.username).isEqualTo(SOME_USERNAME)
        assertThat(actual).isEqualTo(USERNAME_IS_SET_MESSAGE)
    }

    private companion object {
        const val SOME_AUTHENTICATION_DATABASE = "<some authentication database>"
        const val SOME_USERNAME = "<some username>"
        const val AUTHENTICATION_IS_ENABLED_MESSAGE = "AUTHENTICATION_IS_ENABLED_MESSAGE"
        const val AUTHENTICATION_IS_DISABLED_MESSAGE = "AUTHENTICATION_IS_DISABLED_MESSAGE"
        const val AUTHENTICATION_DATABASE_IS_SET_MESSAGE = "AUTHENTICATION_DATABASE_IS_SET_MESSAGE"
        const val USERNAME_IS_SET_MESSAGE = "USERNAME_IS_SET_MESSAGE"
    }
}