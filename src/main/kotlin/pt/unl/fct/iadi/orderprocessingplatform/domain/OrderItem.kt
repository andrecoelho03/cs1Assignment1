package pt.unl.fct.iadi.orderprocessingplatform.domain

data class OrderItem(
    val productId: String,
    val quantity: Int,
    val price: Double
)