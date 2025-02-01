package com.example.shopApp.unit.order

import com.example.shopApp.order.OrderAssembler
import com.example.shopApp.order.OrderValidator
import com.example.shopApp.order.model.NewOrderRequest
import com.example.shopApp.product.ProductService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.math.BigDecimal
import java.time.Instant

class OrderAssemblerTest : AbstractUnitTest() {

    private val orderValidator: OrderValidator = mock(OrderValidator::class.java)
    private val productService: ProductService = mock(ProductService::class.java)
    private lateinit var orderAssembler: OrderAssembler

    @BeforeEach
    fun setUp() {
        orderAssembler = OrderAssembler(productService, orderValidator)
    }

    @Test
    fun `should calculate price correctly`() {
        // given
        val request = NewOrderRequest(
            userId = 1,
            timestamp = Instant.now(),
            orderedItems = listOf(
                NewOrderRequest.Item(123, 4),
                NewOrderRequest.Item(222, 5)
            )
        )
        val appUserEntity = builders.anAppUserEntity()

        // when
        `when`(productService.getProductById(123)).thenReturn(
            builders.aProduct(id = 123, price = BigDecimal.valueOf(10))
        )
        `when`(productService.getProductById(222)).thenReturn(
            builders.aProduct(id = 222, price = BigDecimal.valueOf(20))
        )

        // then
        val result = orderAssembler.assemble(request, appUserEntity)
        assertThat(result.totalPrice).isEqualTo(BigDecimal.valueOf(140))
    }
}
