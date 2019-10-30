package org.mongeez.shell.config

import mu.KLogger
import mu.KotlinLogging
import org.jline.utils.AttributedString
import org.jline.utils.AttributedStyle
import org.springframework.beans.factory.InjectionPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.shell.jline.PromptProvider

@Configuration
class MongeezShellConfiguration {
    @Bean
    fun myPromptProvider(): PromptProvider {
        return PromptProvider { AttributedString("mongeez-shell:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW)) }
    }

    @Bean
    @Scope("prototype")
    fun logger(injectionPoint: InjectionPoint): KLogger {
        return KotlinLogging.logger(injectionPoint.methodParameter?.containingClass?.name // constructor
                ?: injectionPoint.field?.declaringClass?.name ?: error("unidentified injection type")) // or field injection
    }
}