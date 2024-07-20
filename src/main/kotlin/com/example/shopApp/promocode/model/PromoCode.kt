package com.example.shopApp.promocode.model

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate

@Document
class PromoCode {

    @Id
    var id: String? = null

    @Indexed(unique = true)
    lateinit var code: String

    lateinit var value: BigDecimal

    lateinit var startDate: LocalDate

    lateinit var endDate: LocalDate

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
            return PromoCode().apply {
                id = null
                code = request.code
                value = request.value
                startDate = request.startDate
                endDate = request.endDate
            }
        }
    }
}
