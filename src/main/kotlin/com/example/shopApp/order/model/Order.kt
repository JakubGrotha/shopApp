package com.example.shopApp.order.model

import com.example.shopApp.user.AppUserEntity
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant

@Entity(name = "order_table")
data class Order(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "order_id")
        @JsonManagedReference
        val orderedItems: List<OrderedItem>,
        val totalPrice: BigDecimal,
        @Column(name = "date_of_order")
        val dateOfOrderTimestamp: Instant,
        val isDelivered: Boolean,
        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonBackReference
        val appUserEntity: AppUserEntity?
) {
    override fun toString(): String {
        return "Order(id=$id," +
                " orderedItems=$orderedItems," +
                " totalPrice=$totalPrice," +
                " dateOfOrderTimestamp=$dateOfOrderTimestamp," +
                " isDelivered=$isDelivered)" +
                " userId=${appUserEntity?.id}"
    }
}
