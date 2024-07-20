package com.example.shopApp.order.model

import com.example.shopApp.user.AppUserEntity
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant

@Entity(name = "order_table")
class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @OneToMany(cascade = [CascadeType.ALL], targetEntity = OrderedItem::class)
    @JoinColumn(name = "order_id")
    @JsonManagedReference
    var orderedItems: List<OrderedItem> = emptyList()

    @Column(name = "total_price", nullable = false)
    lateinit var totalPrice: BigDecimal

    @Column(name = "date_of_order", nullable = false)
    lateinit var dateOfOrderTimestamp: Instant

    @Column(name = "is_delivered", nullable = false)
    var isDelivered: Boolean = false

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    var appUserEntity: AppUserEntity? = null

    override fun toString(): String {
        return "Order(id=$id," +
                " orderedItems=$orderedItems," +
                " totalPrice=$totalPrice," +
                " dateOfOrderTimestamp=$dateOfOrderTimestamp," +
                " isDelivered=$isDelivered)" +
                " userId=${appUserEntity?.id}"
    }
}
