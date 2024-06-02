package com.example.shopApp.email

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailService(
        private val emailConfiguration: EmailConfiguration,
        private val mailSender: JavaMailSender
) {

    fun sendRegistrationEmail(receiver: String) {
        val message = composeRegistrationEmail(receiver)
        mailSender.send(message)
    }

    private fun composeRegistrationEmail(receiver: String): SimpleMailMessage {
        val message = SimpleMailMessage()
        message.from = emailConfiguration.username
        message.setTo(receiver)
        message.subject = "ShopApp - Sign up confirmation"
        message.text = "Welcome to ShopApp - you are now signed up"
        return message
    }
}