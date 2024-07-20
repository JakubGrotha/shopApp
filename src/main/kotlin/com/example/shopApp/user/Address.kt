package com.example.shopApp.user

import com.example.shopApp.security.model.RegistrationRequest
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "street_address", nullable = false)
    lateinit var streetAddress: String

    @Column(name = "postal_code", nullable = false)
    lateinit var postalCode: String

    @Column(name = "city", nullable = false)
    lateinit var city: String

    @Column(name = "country", nullable = false)
    lateinit var country: String

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    @JsonBackReference
    var appUserEntity: AppUserEntity? = null

    override fun toString(): String {
        return "Address(id=$id," +
                " streetAddress='$streetAddress'," +
                " postalCode='$postalCode'," +
                " city='$city'," +
                " country='$country')" +
                " appUserId=${appUserEntity?.id}"
    }

    companion object {
        fun fromRegistrationRequest(request: RegistrationRequest): Address {
            return Address().apply {
                streetAddress = request.streetAddress
                postalCode = request.postalCode
                city = request.city
                country = request.country
            }
        }
    }
}
