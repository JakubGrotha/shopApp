package com.example.shopApp.integration

import com.example.shopApp.product.ProductRequest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

class ProductControllerTest : AbstractIntegrationTest() {

    companion object {
        private const val PRODUCT_URL = "/product"
    }

    @Test
    fun `should save new product to database`() {
        // given
        val request = ProductRequest("Barbie doll", BigDecimal.valueOf(5.5))

        // when & then
        mockMvc.perform(
                post(PRODUCT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated)
    }
}
