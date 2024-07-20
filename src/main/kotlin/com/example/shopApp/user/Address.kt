package com.example.shopApp.user

import com.example.shopApp.security.model.RegistrationRequest
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
data class Address(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
        val streetAddress: String,
        val postalCode: String,
        val city: String,
        val country: String,
        @OneToOne
        @JoinColumn(name = "user_id", unique = true)
        @JsonBackReference
        var appUser: AppUser?
) {
    override fun toString(): String {
        return "Address(id=$id," +
                " streetAddress='$streetAddress'," +
                " postalCode='$postalCode'," +
                " city='$city'," +
                " country='$country')" +
                " appUserId=${appUser?.id}"
    }

    companion object {
        fun fromRegistrationRequest(request: RegistrationRequest): Address {
            return Address(
                    id = null,
                    streetAddress = request.streetAddress,
                    postalCode = request.postalCode,
                    city = request.city,
                    country = request.country,
                    appUser = null
            )
        }
    }
}
