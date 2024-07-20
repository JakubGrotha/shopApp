package com.example.shopApp.order

import com.example.shopApp.order.model.NewOrderRequest
import com.example.shopApp.order.model.Order
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/order")
class OrderController(
        private val orderService: OrderService
) {

    @GetMapping("/{orderId}")
    fun getOrderById(@PathVariable("orderId") orderId: Long): Order {
        return orderService.getOrderById(orderId)
    }

    @GetMapping
    fun getOrderPageForUser(
            @RequestParam("pageNumber") pageNumber: Int,
            @RequestParam("pageSize") pageSize: Int,
            principal: Principal
    ): ResponseEntity<Page<Order>> {
        val page = orderService.getOrderPageForUser(pageNumber, pageSize, principal)
        return ResponseEntity(page, HttpStatus.OK)
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun placeOrder(@RequestBody request: NewOrderRequest) {
        orderService.addNewOrder(request)
    }
}