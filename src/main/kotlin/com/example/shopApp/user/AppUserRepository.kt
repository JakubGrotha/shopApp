package com.example.shopApp.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepository : JpaRepository<AppUser, Long> {

    fun findAppUserByEmail(email: String): AppUser?

    fun existsByEmail(email: String): Boolean
}
