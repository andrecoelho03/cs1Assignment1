package pt.unl.fct.iadi.orderprocessingplatform.payment

import pt.unl.fct.iadi.orderprocessingplatform.domain.PaymentRequest
import pt.unl.fct.iadi.orderprocessingplatform.domain.Receipt

interface PaymentGateway {
    fun processPayment(paymentRequest: PaymentRequest): Receipt
}