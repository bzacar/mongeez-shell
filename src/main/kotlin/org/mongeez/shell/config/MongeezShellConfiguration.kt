package org.mongeez.shell.config

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import mu.KLogger
import mu.KotlinLogging
import org.jline.utils.AttributedString
import org.jline.utils.AttributedStyle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.shell.jline.PromptProvider

@Configuration
class MongeezShellConfiguration {
    @Bean
    fun promptProvider(): PromptProvider {
        return PromptProvider { AttributedString("mongeez-shell:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW)) }
    }

    @Bean
    @Scope("prototype")
    fun logger(injectionPoint: InjectionPoint): KLogger {
        return KotlinLogging.logger(injectionPoint.methodParameter?.containingClass?.name // constructor
                ?: injectionPoint.field?.declaringClass?.name ?: error("unidentified injection type")) // or field injection
    }

    @Bean
    fun rootLogger(): ch.qos.logback.classic.Logger {
        return LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as? ch.qos.logback.classic.Logger
                ?: throw IllegalStateException("Unexpected type of logger!")
    }

    @Bean
    fun consoleLogAppender(rootLogger: ch.qos.logback.classic.Logger): ConsoleAppender<ILoggingEvent> {
        return ConsoleAppender<ILoggingEvent>().apply {
            context = rootLogger.loggerContext
            name = "CONSOLE"
            encoder = PatternLayoutEncoder().apply {
                context = rootLogger.loggerContext
                pattern = "[%-5level] %logger{36} - %msg%n"
                start()
            }
            start()
        }
    }
}