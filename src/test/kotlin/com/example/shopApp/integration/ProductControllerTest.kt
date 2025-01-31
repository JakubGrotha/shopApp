package com.example.shopApp.integration

import com.example.shopApp.product.Product
import com.example.shopApp.product.ProductRequest
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal

class ProductControllerTest : AbstractIntegrationTest() {

    @Test
    fun `should save new product to database`() {
        // given
        val request = builders.aProductRequest(name = "Barbie doll", price = BigDecimal.valueOf(5.5))

        // when & then
        mockMvc.perform(
            post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated)
    }

    @Test
    fun `should return all products in csv format`() {
        // given
        val products = listOf(
            getProduct("Product1", BigDecimal.valueOf(1), 10),
            getProduct("Product2", BigDecimal.valueOf(2), 20),
            getProduct("Product3", BigDecimal.valueOf(3), 30),
            getProduct("Product4", BigDecimal.valueOf(4), 40)
        )
        productRepository.saveAll(products)

        // when & then
        mockMvc.perform(get("/product/csv"))
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                status().isOk,
                header().string(HttpHeaders.CONTENT_TYPE, "text/csv"),
                header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.csv"),
                content().string(
                    """
                        Name;Price;Quantity
                        Product1;1;10
                        Product2;2;20
                        Product3;3;30
                        Product4;4;40
                        
                    """.trimIndent()
                )
            )
    }

    private fun getProduct(name: String, price: BigDecimal, quantity: Int): Product =
        Product().apply { this.name = name; this.price = price; this.quantity = quantity }
}
