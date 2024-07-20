package com.example.shopApp.product

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "price", nullable = false)
    lateinit var price: BigDecimal

    @Column(name = "quantity", nullable = false)
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
