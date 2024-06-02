package com.example.shopApp.unit.order

import com.example.shopApp.order.OrderValidator
import com.example.shopApp.order.model.NewOrderRequest
import com.example.shopApp.product.Product
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.math.BigDecimal
import kotlin.random.Random

class OrderValidatorTest {

    private val validator = OrderValidator()

    @Test
    fun `should pass validation if order is correct`() {
        // given
        val validPairsList = listOf(
            createItemWithQuantity(3) to createProductWithQuantity(4),
            createItemWithQuantity(1) to createProductWithQuantity(1),
            createItemWithQuantity(10) to createProductWithQuantity(12)
        )

        // when & then
        assertDoesNotThrow { validator.validate(validPairsList) }
    }

    companion object {

        private fun createItemWithQuantity(quantity: Int) = NewOrderRequest.Item(Random.nextLong(), quantity)

        private fun createProductWithQuantity(quantity: Int) =
            Product(Random.nextLong(), "name", BigDecimal.TEN, quantity)
    }
}