package com.example.shopApp.integration

import com.example.shopApp.product.ProductRequest
import com.example.shopApp.security.model.RegistrationRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.client.RestClient
import java.math.BigDecimal

class AuthenticationControllerTest : AbstractIntegrationTest() {

    @LocalServerPort
    private var port: Int = 0

    @Test
    fun `register should generate JWT`() {
        // given
        val registrationRequest = RegistrationRequest(
            email = "test@test.com",
            password = "password",
            streetAddress = "streetAddress",
            postalCode = "00-000",
            city = "city",
            country = "country",
        )

        // when
        val response = RestClient.create("http://127.0.0.1:$port").post()
            .uri("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .body(registrationRequest)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(String::class.java)

        val node = objectMapper.readTree(response.body)

        // then
        assertThat(node).hasSize(1)
        assertThat(node.get("jwtToken")).isNotNull
    }

    @Test
    fun `JWT should be valid to add a product`() {
        // given
        val registrationRequest = RegistrationRequest(
            email = "test@test.com",
            password = "password",
            streetAddress = "streetAddress",
            postalCode = "00-000",
            city = "city",
            country = "country",
        )

        val response = RestClient.create("http://127.0.0.1:$port").post()
            .uri("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .body(registrationRequest)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(String::class.java)

        val jwt = objectMapper.readTree(response.body).get("jwtToken").asText()

        val productRequest = ProductRequest(name = "Name", price = BigDecimal("100.00"))

        // when
        mockMvc.perform(
            post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest))
                .header("Authorization", "Bearer $jwt")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }
}
