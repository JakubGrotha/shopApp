package com.example.shopApp.common

import com.example.shopApp.order.model.NewOrderRequest
import com.example.shopApp.order.model.Order
import com.example.shopApp.product.Product
import com.example.shopApp.product.ProductRequest
import com.example.shopApp.promocode.model.NewPromoCodeRequest
import com.example.shopApp.security.model.RegistrationRequest
import com.example.shopApp.user.AddressEntity
import com.example.shopApp.user.AppUserEntity
import com.example.shopApp.user.AppUserRole
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

    fun anAddressEntity(
        id: Long = 1,
        streetAddress: String = "Tuwima 1",
        city: String = "Warsaw",
        postalCode: String = "00-000",
        country: String = "Poland",
    ) = AddressEntity().apply {
        this.id = id
        this.streetAddress = streetAddress
        this.city = city
        this.postalCode = postalCode
        this.country = country
    }

    fun anAppUserEntity(
        id: Long = 1,
        email: String = "john@example.com",
        password: String = "password",
        role: AppUserRole = AppUserRole.CUSTOMER,
        addressEntity: AddressEntity = anAddressEntity(),
        orders: List<Order> = emptyList(),
    ) = AppUserEntity().apply {
        this.id = id
        this.email = email
        this.password = password
        this.role = role
        this.addressEntity = addressEntity
        this.orders = orders
    }

    fun aProduct(
        id: Long = 123,
        name: String = "product",
        price: BigDecimal = BigDecimal.valueOf(10),
        quantity: Int = Integer.MAX_VALUE,
    ) = Product().apply {
        this.id = id
        this.name = name
        this.price = price
        this.quantity = quantity
    }

    fun anItem(
        productId: Long = 1,
        quantity: Int = 1
    ) = NewOrderRequest.Item(
        productId = productId,
        quantity = quantity
    )
}