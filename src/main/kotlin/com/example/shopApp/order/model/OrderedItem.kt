package com.example.shopApp.order.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class OrderedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    var order: Order? = null

    @Column(name = "product_id", nullable = false)
    var productId: Long? = null

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "quantity", nullable = false)
    var quantity: Int = 0

    @Column(name = "price_per_item", nullable = false)
    lateinit var pricePerItem: BigDecimal

    override fun toString(): String {
        return "OrderedItem(id=$id," +
                " orderId=${order?.id}," +
                " productId=$productId," +
                " name='$name'," +
                " quantity=$quantity," +
                " price=$pricePerItem)"
    }
}
