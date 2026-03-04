package pt.unl.fct.iadi.orderprocessingplatform.payment

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import pt.unl.fct.iadi.orderprocessingplatform.domain.PaymentRequest
import pt.unl.fct.iadi.orderprocessingplatform.domain.Receipt
import pt.unl.fct.iadi.orderprocessingplatform.domain.ReceiptStatus
import java.util.UUID

@Component
@Profile("prod")
class StripeLikePaymentGateway: PaymentGateway  {
    override fun processPayment(paymentRequest: PaymentRequest): Receipt {
        if (paymentRequest.amount <= 0) {
            return Receipt(
                paymentRequest.orderId,
                ReceiptStatus.REJECTED,
                mapOf<String, Any>(
                    "gateway" to "stripe-like",
                    "reason" to "Invalid amount",
                    "amount" to paymentRequest.amount
                )
            )
        } else if (paymentRequest.amount > 10000) {
            return Receipt(
                paymentRequest.orderId,
                ReceiptStatus.FLAGGED_FOR_REVIEW,
                mapOf<String, Any>(
                    "gateway" to "stripe-like",
                    "reason" to "High value transaction requires review",
                    "amount" to paymentRequest.amount
                )
            )
        } else {
            return Receipt(
                paymentRequest.orderId,
                ReceiptStatus.PAID,
                mapOf<String, Any>(
                    "gateway" to "stripe-like",
                    "transactionId" to UUID.randomUUID().toString(),
                    "amount" to paymentRequest.amount
                )
            )
        }
    }
}