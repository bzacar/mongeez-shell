package org.mongeez.shell.parameter.util

import com.mongodb.ServerAddress
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyAll
import mu.KLogger
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mongeez.Mongeez
import org.mongeez.shell.parameter.MONGEEZ_PARAMETERS
import org.springframework.beans.factory.ObjectFactory
import org.springframework.core.io.FileSystemResource

internal class MongeezFactoryTest {
    private val mongeezMock: Mongeez = mockk()
    private val mongeezObjectFactoryMock: ObjectFactory<Mongeez> = mockk {
        every { getObject() } returns mongeezMock
    }
    private val loggerMock: KLogger = mockk()
    private val testSubject = MongeezFactory(mongeezObjectFactoryMock, loggerMock)

    @Test
    fun `create when change set list file parameter is not set`() {
        MONGEEZ_PARAMETERS.changeSetListFileParameter = null
        assertThatThrownBy { testSubject.create() }
                .isInstanceOf(IllegalStateException::class.java)
        verify { loggerMock.error(any<() -> Any?>()) }
    }

    @Test
    fun `create when change set list file parameter is set but authentication and context disabled`() {
        MONGEEZ_PARAMETERS.apply {
            changeSetListFileParameter = SOME_FILE
            authenticationEnabled = false
        }
        val actual = testSubject.create()
        verifyAll {
            mongeezMock.setFile(FileSystemResource(SOME_FILE))
            mongeezMock.setServerAddress(ServerAddress(HOST_ADDRESS, PORT))
            mongeezMock.setDbName(DATABASE_NAME)
            mongeezMock.setUseMongoShell(USE_MONGO_SHELL)
        }
        assertThat(actual).isEqualTo(mongeezMock)
    }

    @Test
    fun `create when change set list file parameter is set and authentication and context enabled`() {
        MONGEEZ_PARAMETERS.apply {
            changeSetListFileParameter = SOME_FILE
            authenticationEnabled = true
            username = SOME_USERNAME
            password = SOME_PASSWORD
            context = SOME_CONTEXT
        }
        val actual = testSubject.create()
        verifyAll {
            mongeezMock.setFile(FileSystemResource(SOME_FILE))
            mongeezMock.setServerAddress(ServerAddress(HOST_ADDRESS, PORT))
            mongeezMock.setDbName(DATABASE_NAME)
            mongeezMock.setUseMongoShell(USE_MONGO_SHELL)
            mongeezMock.setAuth(any())
            mongeezMock.setContext(SOME_CONTEXT)
        }
        assertThat(actual).isEqualTo(mongeezMock)
    }

    private companion object {
        const val SOME_FILE = "<some file>"
        const val SOME_USERNAME = "<some username>"
        val SOME_PASSWORD = "<some password>".toCharArray()
        const val SOME_CONTEXT = "<some context>"
        const val HOST_ADDRESS: String = "localhost"
        const val PORT: Int = 27017
        const val DATABASE_NAME: String = "test"
        const val USE_MONGO_SHELL: Boolean = true
    }
}