package com.example.shopApp.order

import com.example.shopApp.order.exception.OrderNotFoundException
import com.example.shopApp.order.model.NewOrderRequest
import com.example.shopApp.order.model.Order
import com.example.shopApp.user.AppUserRepository
import com.example.shopApp.user.exception.UserNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val appUserRepository: AppUserRepository,
    private val orderAssembler: OrderAssembler
) {

    fun getOrderById(orderId: Long): Order {
        return orderRepository.findById(orderId)
                .orElseThrow { OrderNotFoundException("No order found with the following id: $orderId") }
    }

    fun addNewOrder(request: NewOrderRequest) {
        val appUser = appUserRepository.findById(request.userId)
                .orElseThrow { UserNotFoundException("No user found with the following id: ${request.userId}") }
        val order = orderAssembler.assemble(request, appUser)
        order.orderedItems.forEach { it.order = order }
        orderRepository.save(order)
    }

    fun getOrderPageForUser(pageNumber: Int, pageSize: Int, principal: Principal): Page<Order> {
        val pageable: Pageable = PageRequest.of(pageNumber, pageSize)
        val user = appUserRepository.findAppUserByEmail(principal.name)
                ?: throw UserNotFoundException("No user found with the following email: ${principal.name}")
        return orderRepository.findAllByAppUser(user, pageable)
    }
}
