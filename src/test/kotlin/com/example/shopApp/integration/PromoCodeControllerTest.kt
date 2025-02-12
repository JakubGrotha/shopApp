package com.example.shopApp.integration

import com.example.shopApp.promocode.model.NewPromoCodeRequest
import com.example.shopApp.promocode.model.PromoCode
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.LocalDate

class PromoCodeControllerTest : AbstractIntegrationTest() {

    @Test
    fun `should add new promo code`() {
        // given
        val request = NewPromoCodeRequest(
            code = "XYZ123",
            value = BigDecimal.valueOf(0.1),
            startDate = LocalDate.of(2023, 10, 9),
            endDate = LocalDate.of(2023, 11, 9)
        )

        // when & then
        mockMvc.perform(
            post("/promo-code/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated)
    }

    @Test
    fun `should not add new promo code if there exists another one with the same code value`() {
        // given
        val request = builders.aNewPromoCodeRequest(
            code = "XYZ123",
            value = BigDecimal.valueOf(0.1),
            startDate = LocalDate.of(2023, 10, 9),
            endDate = LocalDate.of(2023, 11, 9)
        )
        val promoCode = PromoCode().apply {
            code = "XYZ123"
            value = BigDecimal.valueOf(0.2)
            startDate = LocalDate.of(2022, 1, 1)
            endDate = LocalDate.of(2022, 2, 1)
        }
        promoCodeMongoRepository.save(promoCode)

        // when & then
        mockMvc.perform(
            post("/promo-code/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isBadRequest)

    }

    @Test
    fun `should remove existing promo code`() {
        // given
        val promoCode = PromoCode().apply {
            code = "XYZ999"
            value = BigDecimal.valueOf(0.1)
            startDate = LocalDate.of(2023, 10, 9)
            endDate = LocalDate.of(2023, 11, 9)
        }
        promoCodeMongoRepository.save(promoCode)

        // when & then
        mockMvc.perform(
            delete("/promo-code/${promoCode.code}")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNoContent)

        assertThat(promoCodeMongoRepository.findById(promoCode.code)).isEmpty()
    }
}
