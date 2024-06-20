package com.company.payment_service.model

import com.company.payment_service.model.enums.Currency
import com.company.payment_service.model.enums.PaymentMethod
import com.company.payment_service.model.enums.PaymentStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payments")
data class Payment @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = "",

    val orderId: String,
    val amount: BigDecimal,
    val paymentDate: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    val paymentMethod: PaymentMethod,

    @Enumerated(EnumType.STRING)
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING,

    @Enumerated(EnumType.STRING)
    val currency: Currency


)
