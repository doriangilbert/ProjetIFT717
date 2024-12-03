package com.example.projetift717.repository

import com.example.projetift717.model.Order
import com.example.projetift717.network.OrderService

class OrderRepository(private val orderService: OrderService) {
    suspend fun fetchAll(): List<Order> {
        return orderService.fetchAll()
    }

    suspend fun fetch(id: String): Order {
        return orderService.fetch(id)
    }

    suspend fun create(order: Order): Order {
        return orderService.create(order)
    }

    suspend fun update(id: String, order: Order): Order {
        return orderService.update(id, order)
    }

    suspend fun delete(id: String): Order {
        return orderService.delete(id)
    }

}