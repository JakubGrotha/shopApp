package com.example.shopApp.security

import com.example.shopApp.security.jwt.JwtUtils
import com.example.shopApp.security.model.AuthenticationResponse
import com.example.shopApp.security.model.LoginRequest
import com.example.shopApp.security.model.RegistrationRequest
import com.example.shopApp.user.AppUserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationService(
        private val authenticationManager: AuthenticationManager,
        private val appUserService: AppUserService,
        private val jwtUtils: JwtUtils
) {

    fun authenticate(request: LoginRequest): AuthenticationResponse {
        val username = request.username
        val password = request.password
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(username, password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val appUser = appUserService.loadUserByUsername(username)
        val token = jwtUtils.generateToken(appUser)
        return AuthenticationResponse(token)
    }

    fun register(request: RegistrationRequest): AuthenticationResponse {
        val appUser = appUserService.registerAppUser(request)
        val token = jwtUtils.generateToken(appUser)
        return AuthenticationResponse(token)
    }
}