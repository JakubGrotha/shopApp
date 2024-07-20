package com.example.shopApp.order

import com.example.shopApp.order.model.OrderedItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderedItemRepository : JpaRepository<OrderedItem, Long>