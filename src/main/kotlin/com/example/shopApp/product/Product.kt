package com.example.shopApp.product

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal

@Entity
data class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
        val name: String,
        val price: BigDecimal,
        val quantity: Int

) {

    companion object {

        fun fromProductRequest(productRequest: ProductRequest): Product {
            return Product(
                    id = null,
                    name = productRequest.name,
                    price = productRequest.price,
                    quantity = 0
            )
        }
    }
}
