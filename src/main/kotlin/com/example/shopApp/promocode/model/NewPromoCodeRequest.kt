package com.example.shopApp.promocode.model

import java.math.BigDecimal
import java.time.LocalDate

data class NewPromoCodeRequest (
        val code: String,
        val value: BigDecimal,
        val startDate: LocalDate,
        val endDate: LocalDate
)
