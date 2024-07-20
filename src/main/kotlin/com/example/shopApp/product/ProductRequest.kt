package com.example.shopApp.product

import java.math.BigDecimal

data class ProductRequest(
        val name: String,
        val price: BigDecimal
)
