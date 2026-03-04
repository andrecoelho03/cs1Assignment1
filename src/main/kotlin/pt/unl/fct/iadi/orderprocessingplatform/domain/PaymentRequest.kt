package pt.unl.fct.iadi.orderprocessingplatform.domain

data class PaymentRequest(
    val orderId: String,
    val amount: Double
)