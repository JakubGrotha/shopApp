package com.example.shopApp.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepository : JpaRepository<AppUserEntity, Long> {

    fun findAppUserByEmail(email: String): AppUserEntity?

    fun existsByEmail(email: String): Boolean
}
