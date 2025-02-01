package com.example.shopApp.user

import com.example.shopApp.order.model.Order
import com.example.shopApp.security.model.RegistrationRequest
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class AppUser(
    val id: Long?,
    val email: String,
    @get:JvmName("password")
    val password: String,
    val role: AppUserRole,
    val addressEntity: AddressEntity,
    val orders: List<Order>
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    companion object {

        fun fromRegistrationRequest(request: RegistrationRequest, encodedPassword: String): AppUser {
            return AppUser(
                id = null,
                email = request.email,
                password = encodedPassword,
                addressEntity = AddressEntity.fromRegistrationRequest(request),
                role = AppUserRole.CUSTOMER,
                orders = emptyList()
            )
        }
    }
}
