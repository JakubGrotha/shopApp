package com.example.shopApp.email

import org.jetbrains.annotations.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated

@Configuration(proxyBeanMethods = false)
@ConfigurationProperties("spring.mail")
@Validated
class EmailConfiguration {

    @NotNull
    var enabled: Boolean = false

    @NotNull
    lateinit var username: String
}