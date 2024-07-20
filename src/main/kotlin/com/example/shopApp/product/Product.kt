package com.example.shopApp.product

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal

@Entity
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    lateinit var name: String

    lateinit var price: BigDecimal

    var quantity: Int = 0

    companion object {

        fun fromProductRequest(productRequest: ProductRequest): Product {
            return Product().apply {
                id = null
                name = productRequest.name
                price = productRequest.price
                quantity = 0
            }
        }
    }
}
