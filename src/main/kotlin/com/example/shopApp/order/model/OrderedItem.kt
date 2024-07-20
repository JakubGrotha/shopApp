package com.example.shopApp.order.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class OrderedItem(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
        @ManyToOne
        @JsonBackReference
        var order: Order?,
        @Column(name = "product_id", nullable = false)
        val productId: Long,
        @Column(name = "name", nullable = false)
        val name: String,
        @Column(name = "quantity", nullable = false)
        val quantity: Int,
        @Column(name = "price_per_item", nullable = false)
        val pricePerItem: BigDecimal
) {
    override fun toString(): String {
        return "OrderedItem(id=$id," +
                " orderId=${order?.id}," +
                " productId=$productId," +
                " name='$name'," +
                " quantity=$quantity," +
                " price=$pricePerItem)"
    }
}
