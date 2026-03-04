package pt.unl.fct.iadi.orderprocessingplatform

import org.springframework.aop.support.AopUtils
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import pt.unl.fct.iadi.orderprocessingplatform.domain.Order
import pt.unl.fct.iadi.orderprocessingplatform.domain.OrderItem
import pt.unl.fct.iadi.orderprocessingplatform.domain.PaymentRequest
import pt.unl.fct.iadi.orderprocessingplatform.payment.PaymentGateway
import pt.unl.fct.iadi.orderprocessingplatform.pricing.PriceCalculator
import java.time.Instant
import java.util.Locale

@Component
class OrderProcessor(
    private val priceCalculator: PriceCalculator,
    private val paymentGateway: PaymentGateway
): CommandLineRunner {
    fun processOrder(order: Order): List<String> {
        var totalPrice = 0.0
        val output = mutableListOf<String>()

        output.add("Order ID: ${order.id}")
        output.add("User ID: ${order.userId}")
        output.add("Created at: ${order.createdAt}")
        output.add("")

        output.add("Items:")

        order.items.forEach { item ->
            val itemTotal = priceCalculator.calculateTotalPrice(item)
            totalPrice += itemTotal
            output.add("  - ${item.productId}: ${item.quantity} x $${item.price} = $${String.format("%.2f", itemTotal)}")
        }
        output.add("")

        val roundedPrice = String.format(Locale.US, "%.2f", totalPrice).toDouble()

        val paymentRequest = PaymentRequest(
            order.id,
            roundedPrice
        )

        val receipt = paymentGateway.processPayment(paymentRequest)

        val targetClass = AopUtils.getTargetClass(priceCalculator)
        val calculatorName = targetClass.simpleName ?: targetClass.name

        output.add("Total Price: $$roundedPrice")
        output.add("Calculator Used: $calculatorName")
        output.add("")

        output.add("Payment Status: ${receipt.status}")
        val gateway = receipt.metadata["gateway"]
        if (gateway != null) {
            output.add("Payment Gateway: $gateway")

        }
        val transactionId = receipt.metadata["transactionId"]
        if (transactionId != null) {
            output.add("Transaction ID: $transactionId")
        }
        val reason = receipt.metadata["reason"]
        if (reason != null) {
            output.add("Reason: $reason")
        }
        output.add("")

        output.add("=== Processing Complete ===")

        return output
    }

    override fun run(vararg args: String) {
        val orderItem1 = OrderItem("0", 2, 15.90)
        val orderItem2 = OrderItem("1", 4, 4.99)
        val orderItem3 = OrderItem("2", 6, 0.99)
        val orderItem4 = OrderItem("3", 1, 49.90)
        val orderItem5 = OrderItem("4", 2, 9.99)
        val order = Order("0", listOf(orderItem1, orderItem2, orderItem3, orderItem4, orderItem5), "0", Instant.now())
        processOrder(order).forEach { println(it) }
        return
    }
}