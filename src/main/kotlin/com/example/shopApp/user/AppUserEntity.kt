package com.example.shopApp.user

import com.example.shopApp.order.model.Order
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "app_user")
open class AppUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open var id: Long? = null

    @Column(name = "email", nullable = false)
    open lateinit var email: String

    @Column(name = "password", nullable = false)
    open lateinit var password: String

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    open lateinit var role: AppUserRole

    @OneToOne(mappedBy = "appUserEntity", cascade = [CascadeType.ALL])
    @JsonManagedReference
    open lateinit var address: Address

    @OneToMany(mappedBy = "appUserEntity")
    @JsonManagedReference
    open lateinit var orders: List<Order>

    fun toAppUser(): AppUser =
        AppUser(
            id = this.id,
            email = this.email,
            password = this.password,
            role = this.role,
            address = this.address,
            orders = this.orders
        )

    companion object {

        fun fromAppUser(appUser: AppUser): AppUserEntity =
            AppUserEntity().apply {
                id = appUser.id
                email = appUser.email
                password = appUser.password
                role = appUser.role
                address = appUser.address
                orders = appUser.orders
            }
    }
}
