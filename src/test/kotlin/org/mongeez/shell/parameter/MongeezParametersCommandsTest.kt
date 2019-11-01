package org.mongeez.shell.parameter

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mongeez.shell.parameter.MongeezParametersCommands.Companion.CONTEXT_PROPERTY_NAME
import org.mongeez.shell.parameter.MongeezParametersCommands.Companion.FILE_PATH_PROPERTY_NAME
import org.mongeez.shell.parameter.MongeezParametersCommands.Companion.USING_DB_EVAL_PROPERTY_NAME
import org.mongeez.shell.parameter.MongeezParametersCommands.Companion.USING_MONGO_SHELL_PROPERTY_NAME
import org.mongeez.shell.parameter.util.OutputMessageProvider

internal class MongeezParametersCommandsTest {
    private val outputMessageProviderMock: OutputMessageProvider = mockk {
        every { getSetMethodMessage(FILE_PATH_PROPERTY_NAME, SOME_PATH) } returns CHANGE_SET_LIST_FILE_IS_SET_MESSAGE
        every { getSetMethodMessage(CONTEXT_PROPERTY_NAME, SOME_CONTEXT) } returns CONTEXT_IS_SET_MESSAGE
        every { getEnableMethodMessage(USING_MONGO_SHELL_PROPERTY_NAME) } returns MONGO_SHELL_IS_ENABLED_MESSAGE
        every { getEnableMethodMessage(USING_DB_EVAL_PROPERTY_NAME) } returns DB_EVAL_IS_ENABLED_MESSAGE
    }
    private val testSubject = MongeezParametersCommands(outputMessageProviderMock)

    @Test
    fun setChangeSetListFile() {
        val actual = testSubject.setChangeSetListFile(SOME_PATH)
        assertThat(MONGEEZ_PARAMETERS.changeSetListFileParameter).isEqualTo(SOME_PATH)
        assertThat(actual).isEqualTo(CHANGE_SET_LIST_FILE_IS_SET_MESSAGE)
    }

    @Test
    fun setContext() {
        val actual = testSubject.setContext(SOME_CONTEXT)
        assertThat(MONGEEZ_PARAMETERS.context).isEqualTo(SOME_CONTEXT)
        assertThat(actual).isEqualTo(CONTEXT_IS_SET_MESSAGE)
    }

    @Test
    fun enableMongoShell() {
        val actual = testSubject.enableMongoShell()
        assertThat(MONGEEZ_PARAMETERS.useMongoShell).isTrue()
        assertThat(actual).isEqualTo(MONGO_SHELL_IS_ENABLED_MESSAGE)
    }

    @Test
    fun enableDbEval() {
        val actual = testSubject.enableDbEval()
        assertThat(MONGEEZ_PARAMETERS.useMongoShell).isFalse()
        assertThat(actual).isEqualTo(DB_EVAL_IS_ENABLED_MESSAGE)
    }

    private companion object {
        const val SOME_PATH = "<some path>"
        const val SOME_CONTEXT = "<some context>"
        const val CHANGE_SET_LIST_FILE_IS_SET_MESSAGE = "CHANGE_SET_LIST_FILE_IS_SET_MESSAGE"
        const val CONTEXT_IS_SET_MESSAGE = "CONTEXT_IS_SET_MESSAGE"
        const val MONGO_SHELL_IS_ENABLED_MESSAGE = "MONGO_SHELL_IS_ENABLED_MESSAGE"
        const val DB_EVAL_IS_ENABLED_MESSAGE = "DB_EVAL_IS_ENABLED_MESSAGE"
    }
}