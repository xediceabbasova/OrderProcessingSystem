package com.company.order_service.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = "",

    val userEmail: String,
    val productId: Long,
    val quantity: Long,
    val totalAmount: BigDecimal,
    val orderDate: LocalDateTime = LocalDateTime.now(),
    val deliveryAddress: String,

    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus = OrderStatus.PENDING

) {
    fun updateOrderStatus(newStatus: OrderStatus) {
        orderStatus = newStatus
    }
}
