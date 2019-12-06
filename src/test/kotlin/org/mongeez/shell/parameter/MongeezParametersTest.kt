package org.mongeez.shell.parameter

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal class MongeezParametersTest {

    @Test
    fun `get change set list file when parameter is not set`() {
        val testSubject = MongeezParameters().apply {
            changeSetListFileParameter = null
        }
        assertThatThrownBy { testSubject.changeSetListFile }
                .isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `get change set list file when parameter is set`() {
        val testSubject = MongeezParameters().apply {
            changeSetListFileParameter = SOME_FILE
        }
        val actual = testSubject.changeSetListFile
        assertThat(actual).isEqualTo(SOME_FILE)
    }

    @Test
    fun `get mongoAuth when authentication is disabled`() {
        val testSubject = MongeezParameters().apply {
            authenticationEnabled = false
        }
        val actual = testSubject.mongoAuth
        assertThat(actual).isNull()
    }

    @Test
    fun `get mongoAuth when authentication is enabled but username is null`() {
        val testSubject = MongeezParameters().apply {
            authenticationEnabled = true
            username = null
        }
        assertThatThrownBy { testSubject.mongoAuth }
                .isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `get mongoAuth when authentication is enabled and username is not null but password is null`() {
        val testSubject = MongeezParameters().apply {
            authenticationEnabled = true
            username = SOME_USERNAME
            password = null
        }
        assertThatThrownBy { testSubject.mongoAuth }
                .isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `get mongoAuth when authentication is enabled and username and password are not null`() {
        val testSubject = MongeezParameters().apply {
            authenticationEnabled = true
            username = SOME_USERNAME
            password = SOME_PASSWORD
        }
        val actual = testSubject.mongoAuth
        assertThat(actual)
                .extracting("username", "password", "authDb")
                .containsExactly(SOME_USERNAME, SOME_PASSWORD, AUTHENTICATION_DATABASE)
    }

    private companion object {
        const val SOME_FILE = "<some file>"
        const val SOME_USERNAME = "<some username>"
        val SOME_PASSWORD = "<some password>".toCharArray()
        const val AUTHENTICATION_DATABASE = "admin"
    }
}