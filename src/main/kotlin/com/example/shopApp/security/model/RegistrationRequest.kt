package com.example.shopApp.security.model

data class RegistrationRequest(
        val email: String,
        val password: String,
        val streetAddress: String,
        val postalCode: String,
        val city: String,
        val country: String
)
