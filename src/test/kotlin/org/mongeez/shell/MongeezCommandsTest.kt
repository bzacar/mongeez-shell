package org.mongeez.shell

import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import mu.KLogger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mongeez.Mongeez
import org.mongeez.data.DryRunResult
import org.mongeez.shell.parameter.MONGEEZ_PARAMETERS
import org.mongeez.shell.parameter.util.MongeezFactory

internal class MongeezCommandsTest {
    private val dryRunResultMock: DryRunResult = mockk()
    private val mongeezMock: Mongeez = mockk()
    private val mongeezFactoryMock: MongeezFactory = mockk {
        every { create() } returns mongeezMock
    }
    private val loggerMock: KLogger = mockk()
    private val testSubject = MongeezCommands(mongeezFactoryMock, loggerMock)

    @Test
    fun `successful run`() {
        val actual = testSubject.run()
        assertThat(actual).isEqualTo("Run completed!")
        verify { mongeezMock.process() }
    }

    @Test
    fun `failed run`() {
        every { mongeezMock.process() } throws IllegalStateException("Something went wrong")
        val actual = testSubject.run()
        assertThat(actual).isEqualTo("Run have failed!")
        verifyOrder {
            mongeezMock.process()
            loggerMock.error(any<() -> Any?>())
        }
    }

    @Test
    fun `successful dry-run`() {
        every { mongeezMock.dryRun() } returns dryRunResultMock
        val actual = testSubject.dryRun()
        assertThat(actual).isEqualTo("")
        verify {
            mongeezMock.dryRun()
            dryRunResultMock.print(any())
        }
    }

    @Test
    fun `failed dry-run`() {
        every { mongeezMock.dryRun() } throws IllegalStateException("Something went wrong")
        val actual = testSubject.dryRun()
        assertThat(actual).isEqualTo("Dry-run have failed!")
        verifyOrder {
            mongeezMock.dryRun()
            loggerMock.error(any<() -> Any?>())
        }
        verify { dryRunResultMock wasNot Called }
    }

    @Test
    fun `check availability when change set list file is not set`() {
        MONGEEZ_PARAMETERS.changeSetListFileParameter = null
        val actual = testSubject.availabilityCheck()
        assertThat(actual.isAvailable).isFalse()
        assertThat(actual.reason).isNotNull()
                .isEqualTo("change set list file path is not provided")
    }

    @Test
    fun `check availability when change set list file is set but authentication is enabled and username and password not provided`() {
        MONGEEZ_PARAMETERS.apply {
            changeSetListFileParameter = SOME_FILE
            authenticationEnabled = true
            username = null
            password = null
        }
        val actual = testSubject.availabilityCheck()
        assertThat(actual.isAvailable).isFalse()
        assertThat(actual.reason).isNotNull()
                .isEqualTo("authentication is enabled but username or password is missing")
    }

    @Test
    fun `check availability when change set list file is set but authentication is enabled and password not provided`() {
        MONGEEZ_PARAMETERS.apply {
            changeSetListFileParameter = SOME_FILE
            authenticationEnabled = true
            username = SOME_USERNAME
            password = null
        }
        val actual = testSubject.availabilityCheck()
        assertThat(actual.isAvailable).isFalse()
        assertThat(actual.reason).isNotNull()
                .isEqualTo("authentication is enabled but username or password is missing")
    }

    @Test
    fun `check availability when change set list file is set and authentication is enabled with credentials`() {
        MONGEEZ_PARAMETERS.apply {
            changeSetListFileParameter = SOME_FILE
            authenticationEnabled = true
            username = SOME_USERNAME
            password = SOME_PASSWORD
        }
        val actual = testSubject.availabilityCheck()
        assertThat(actual.isAvailable).isTrue()
        assertThat(actual.reason).isNull()
    }

    @Test
    fun `check availability when change set list file is set and authentication is disabled`() {
        MONGEEZ_PARAMETERS.apply {
            changeSetListFileParameter = SOME_FILE
            authenticationEnabled = false
            username = null
            password = null
        }
        val actual = testSubject.availabilityCheck()
        assertThat(actual.isAvailable).isTrue()
        assertThat(actual.reason).isNull()
    }

    private companion object {
        const val SOME_FILE = "<some file>"
        const val SOME_USERNAME = "<some username>"
        val SOME_PASSWORD = "<some password>".toCharArray()
    }
}