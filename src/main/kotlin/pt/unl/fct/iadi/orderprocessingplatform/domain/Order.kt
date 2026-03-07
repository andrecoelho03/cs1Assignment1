package pt.unl.fct.iadi.orderprocessingplatform.domain

import java.time.Instant

data class Order(
    val id: String,
    val items: List<OrderItem>,
    val userId: String,
    val createdAt: Instant = Instant.now()
) {
    class OrderItem(
        val productId: String,
        val quantity: Int,
        val price: Double
    )
}