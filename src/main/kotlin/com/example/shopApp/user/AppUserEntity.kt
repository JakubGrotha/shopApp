package com.example.shopApp.user

import com.example.shopApp.order.model.Order
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class AppUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "email", nullable = false)
    lateinit var email: String

    @Column(name = "password", nullable = false)
    lateinit var password: String

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    lateinit var role: AppUserRole

    @OneToOne(mappedBy = "appUserEntity", cascade = [CascadeType.ALL])
    @JsonManagedReference
    lateinit var addressEntity: AddressEntity

    @OneToMany(mappedBy = "appUserEntity", targetEntity = Order::class)
    @JsonManagedReference
    lateinit var orders: List<Order>

    fun toAppUser(): AppUser =
        AppUser(
            id = this.id,
            email = this.email,
            password = this.password,
            role = this.role,
            addressEntity = this.addressEntity,
            orders = this.orders
        )

    companion object {

        fun fromAppUser(appUser: AppUser): AppUserEntity =
            AppUserEntity().apply {
                id = appUser.id
                email = appUser.email
                password = appUser.password
                role = appUser.role
                addressEntity = appUser.addressEntity
                orders = appUser.orders
            }
    }
}
