package com.example.shopApp.unit.order

import com.example.shopApp.order.OrderAssembler
import com.example.shopApp.order.OrderValidator
import com.example.shopApp.order.model.NewOrderRequest
import com.example.shopApp.product.Product
import com.example.shopApp.product.ProductService
import com.example.shopApp.user.Address
import com.example.shopApp.user.AppUser
import com.example.shopApp.user.AppUserRole
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.math.BigDecimal
import java.time.Instant

class OrderAssemblerTest {

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
        val appUser = createUser()

        // when
        `when`(productService.getProductById(123)).thenReturn(
            Product(id = 123, name = "product", price = BigDecimal.valueOf(10), quantity = Integer.MAX_VALUE ))
        `when`(productService.getProductById(222)).thenReturn(
            Product(id = 222, name = "product", price = BigDecimal.valueOf(20), quantity = Integer.MAX_VALUE ))

        // then
        val result = orderAssembler.assemble(request, appUser)
        assertThat(result.totalPrice).isEqualTo(BigDecimal.valueOf(140))
    }

    companion object {

        private fun createUser(): AppUser =
            AppUser(
                id = 1,
                email = "john@example.com",
                password = "password",
                role = AppUserRole.CUSTOMER,
                address = Address(
                    id = null,
                    appUser = null,
                    streetAddress = "Tuwima 1",
                    city = "Warsaw",
                    postalCode = "00-000",
                    country = "Poland"
                ),
                orders = listOf()
            )
    }
}
