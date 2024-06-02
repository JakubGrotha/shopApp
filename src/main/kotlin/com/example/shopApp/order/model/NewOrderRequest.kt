package com.example.shopApp.order.model

import java.time.Instant

data class NewOrderRequest(
        val orderedItems: List<Item>,
        val userId: Long,
        val timestamp: Instant
) {
    data class Item(
            val productId: Long,
            val quantity: Int
    )
}
