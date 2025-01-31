package com.example.shopApp.common

import com.example.shopApp.product.ProductRequest
import com.example.shopApp.promocode.model.NewPromoCodeRequest
import com.example.shopApp.security.model.RegistrationRequest
import java.math.BigDecimal
import java.time.LocalDate

class Builders {

    fun aRegistrationRequest(
        email: String = "test@test.com",
        password: String = "password",
        streetAddress: String = "streetAddress",
        postalCode: String = "00-000",
        city: String = "city",
        country: String = "country",
    ) = RegistrationRequest(
        email = email,
        password = password,
        streetAddress = streetAddress,
        postalCode = postalCode,
        city = city,
        country = country
    )

    fun aNewPromoCodeRequest(
        code: String = "XYZ123",
        value: BigDecimal = BigDecimal.valueOf(0.1),
        startDate: LocalDate = LocalDate.of(2023, 10, 9),
        endDate: LocalDate = LocalDate.of(2023, 11, 9),
    ) = NewPromoCodeRequest(
        code = code,
        value = value,
        startDate = startDate,
        endDate = endDate
    )

    fun aProductRequest(
        name: String = "name",
        price: BigDecimal = BigDecimal.valueOf(10.0),
    ) = ProductRequest(
        name = name,
        price = price
    )
}