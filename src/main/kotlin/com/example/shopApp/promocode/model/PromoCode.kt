package com.example.shopApp.promocode.model

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate

@Document
data class PromoCode(
        @Id
        val id: String?,
        @Indexed(unique = true)
        val code: String,
        val value: BigDecimal,
        val startDate: LocalDate,
        val endDate: LocalDate
) {
    fun toPromoCodeDto(): PromoCodeDto {
        return PromoCodeDto(
                code = this.code,
                value = this.value,
                startDate = this.startDate,
                endDate = this.endDate
        )
    }

    companion object {
        fun fromNewPromoCodeRequest(request: NewPromoCodeRequest): PromoCode {
            return PromoCode(
                    id = null,
                    code = request.code,
                    value = request.value,
                    startDate = request.startDate,
                    endDate = request.endDate
            )
        }
    }
}
