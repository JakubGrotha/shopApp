package com.example.shopApp.order

import com.example.shopApp.order.exception.InvalidOrderException
import com.example.shopApp.order.model.NewOrderRequest
import com.example.shopApp.product.Product
import org.springframework.stereotype.Component

@Component
class OrderValidator {

    private val exceededQuantityError = mutableListOf<Pair<NewOrderRequest.Item, Product>>()

    fun validate(pairs: List<Pair<NewOrderRequest.Item, Product>>) {
        for (pair in pairs) {
            val item = pair.first
            val product = pair.second
            validateQuantity(item, product)
        }
        if (exceededQuantityError.isNotEmpty()) {
            val message = "These products are not available in the number specified in the request. More info:"
            exceededQuantityError.forEach {
                message.plus("\nProduct name: ${it.second.name},")
                    .plus(" requested quantity: ${it.first.quantity},")
                    .plus(" available quantity: ${it.second.quantity}")
            }
            throw InvalidOrderException(message)
        }
    }

    private fun validateQuantity(item: NewOrderRequest.Item, product: Product) {
        if (item.quantity > product.quantity) {
            exceededQuantityError.add(item to product)
        }
    }
}
