package com.example.shopApp.security

import com.example.shopApp.security.model.AuthenticationResponse
import com.example.shopApp.security.model.LoginRequest
import com.example.shopApp.security.model.RegistrationRequest
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(
        private val authenticationService: AuthenticationService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthenticationResponse> {
        val authenticationResponse = authenticationService.authenticate(request)
        return ResponseEntity(authenticationResponse, HttpStatusCode.valueOf(200))
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegistrationRequest): ResponseEntity<AuthenticationResponse> {
        val authenticationResponse = authenticationService.register(request)
        return ResponseEntity(authenticationResponse, HttpStatusCode.valueOf(201))
    }
}
