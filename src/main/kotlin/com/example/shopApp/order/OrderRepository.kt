package com.example.shopApp.order

import com.example.shopApp.order.model.Order
import com.example.shopApp.user.AppUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {

    fun findAllByAppUser(user: AppUser, pageable: Pageable): Page<Order>
}
