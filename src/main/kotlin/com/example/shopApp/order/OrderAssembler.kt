package com.example.shopApp.order

import com.example.shopApp.order.model.NewOrderRequest
import com.example.shopApp.order.model.Order
import com.example.shopApp.order.model.OrderedItem
import com.example.shopApp.product.Product
import com.example.shopApp.product.ProductService
import com.example.shopApp.user.AppUser
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class OrderAssembler(
    private val productService: ProductService,
    private val orderValidator: OrderValidator
) {

    fun assemble(request: NewOrderRequest, appUser: AppUser): Order {
        val productsItemsPairs = request.orderedItems.map { it to productService.getProductById(it.productId) }.toList()
        orderValidator.validate(productsItemsPairs)
        val items = getProductItems(productsItemsPairs)
        return Order(
            id = null,
            orderedItems = items,
            totalPrice = calculateTotalPrice(items),
            appUser = appUser,
            dateOfOrderTimestamp = request.timestamp,
            isDelivered = false
        )
    }

    private fun getProductItems(pairs: List<Pair<NewOrderRequest.Item, Product>>): List<OrderedItem> {
        return pairs.map {
            val item = it.first
            val product = it.second
            OrderedItem(
                id = null,
                order = null,
                productId = item.productId,
                name = product.name,
                quantity = item.quantity,
                pricePerItem = product.price
            )
        }.toList()
    }

    private fun calculateTotalPrice(items: List<OrderedItem>): BigDecimal {
        return items
            .map { it.pricePerItem.multiply(BigDecimal.valueOf(it.quantity.toLong())) }
            .reduce(BigDecimal::add)
    }
}