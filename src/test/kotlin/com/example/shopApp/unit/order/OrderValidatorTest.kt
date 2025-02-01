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
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class OrderValidatorTest : AbstractUnitTest() {

    private val validator = OrderValidator()

    @Test
    fun `should pass validation if order is correct`() {
        // given
        val validPairsList = listOf(
            builders.anItem(quantity = 1) to builders.aProduct(quantity = 4),
            builders.anItem(quantity = 1) to builders.aProduct(quantity = 1),
            builders.anItem(quantity = 10) to builders.aProduct(quantity = 12)
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

    private companion object {

        @JvmStatic
        private fun invalidOrders(): List<Arguments> {
            return listOf(
                arguments(
                    listOf(builders.anItem(quantity = 1) to builders.aProduct(quantity = 0))
                ),
                arguments(
                    listOf(builders.anItem(quantity = 5) to builders.aProduct(quantity = 3))
                ),
                arguments(
                    listOf(
                        builders.anItem(quantity = 2) to builders.aProduct(quantity = 1),
                        builders.anItem(quantity = 3) to builders.aProduct(quantity = 4)
                    )
                ),
                arguments(
                    listOf(
                        builders.anItem(quantity = 2) to builders.aProduct(quantity = 1),
                        builders.anItem(quantity = Integer.MAX_VALUE) to builders.aProduct(quantity = 3),
                        builders.anItem(quantity = 3) to builders.aProduct(quantity = 6)
                    )
                )
            )
        }
    }
}
