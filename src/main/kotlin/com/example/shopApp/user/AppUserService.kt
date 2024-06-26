package com.example.shopApp.user

import com.example.shopApp.email.EmailConfiguration
import com.example.shopApp.email.EmailService
import com.example.shopApp.security.model.RegistrationRequest
import com.example.shopApp.user.exception.UserAlreadyExistsException
import com.example.shopApp.user.exception.UserNotFoundException
import jakarta.transaction.Transactional
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AppUserService(
        private val appUserRepository: AppUserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val emailConfiguration: EmailConfiguration,
        private val emailService: EmailService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val appUser = appUserRepository.findAppUserByEmail(username)
                ?: throw UserNotFoundException("No user found with the following email: $username")
        return User.builder()
                .username(appUser.username)
                .password(appUser.password)
                .accountExpired(!appUser.isAccountNonExpired)
                .accountLocked(!appUser.isAccountNonLocked)
                .credentialsExpired(!appUser.isCredentialsNonExpired)
                .disabled(!appUser.isEnabled)
                .build()
    }

    @Transactional
    fun registerAppUser(request: RegistrationRequest): AppUser {
        val appUserEmail = request.email
        checkIfUserAlreadyExists(appUserEmail)
        val encodedPassword = passwordEncoder.encode(request.password)
        val appUser = AppUser.fromRegistrationRequest(request, encodedPassword)
        appUserRepository.save(appUser)
        sendEmailConfirmation(appUserEmail)
        return appUser
    }

    private fun checkIfUserAlreadyExists(appUserEmail: String) {
        val userExists = appUserRepository.existsByEmail(appUserEmail)
        if (userExists) {
            throw UserAlreadyExistsException("User with the following email: $appUserEmail already exists")
        }
    }

    private fun sendEmailConfirmation(appUserEmail: String) {
        if (emailConfiguration.enabled) {
            emailService.sendRegistrationEmail(appUserEmail)
        }
    }
}
