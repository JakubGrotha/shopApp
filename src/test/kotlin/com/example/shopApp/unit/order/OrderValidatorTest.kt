package com.example.shopApp.unit.order

import com.example.shopApp.order.OrderValidator
import com.example.shopApp.order.exception.InvalidOrderException
import com.example.shopApp.order.model.NewOrderRequest
import com.example.shopApp.product.Product
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
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


    @ParameterizedTest
    @MethodSource("invalidOrders")
    fun `should NOT pass validation if available quantity is lower than requested quantity`(
        pairs: List<Pair<NewOrderRequest.Item, Product>>,
    ) {

        // when & then
        assertThrows<InvalidOrderException> { validator.validate(pairs) }
    }

    companion object {

        private fun createItemWithQuantity(quantity: Int) = NewOrderRequest.Item(Random.nextLong(100), quantity)

        private fun createProductWithQuantity(quantity: Int) =
            Product().apply { id = Random.nextLong(); name = "name"; price = BigDecimal.TEN; this.quantity = quantity }

        @JvmStatic
        private fun invalidOrders(): List<Arguments> {
            return listOf(
                Arguments.arguments(
                    listOf(createItemWithQuantity(1) to createProductWithQuantity(0))
                ),
                Arguments.arguments(
                    listOf(createItemWithQuantity(5) to createProductWithQuantity(3))
                ),
                Arguments.arguments(
                    listOf(
                        createItemWithQuantity(2) to createProductWithQuantity(1),
                        createItemWithQuantity(3) to createProductWithQuantity(4)
                    )
                ),
                Arguments.arguments(
                    listOf(
                        createItemWithQuantity(2) to createProductWithQuantity(1),
                        createItemWithQuantity(Integer.MAX_VALUE) to createProductWithQuantity(3),
                        createItemWithQuantity(3) to createProductWithQuantity(6)
                    )
                )
            )
        }
    }
}
