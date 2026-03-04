package pt.unl.fct.iadi.orderprocessingplatform.pricing

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import pt.unl.fct.iadi.orderprocessingplatform.domain.OrderItem

@Component
@ConditionalOnProperty(prefix = "pricing.promo", name = ["enabled"], havingValue = "true")
class PromoPriceCalculator: PriceCalculator {
    override fun calculateTotalPrice(order: OrderItem): Double {
        return if (order.quantity > 5) {
            order.quantity * order.price * 0.8
        } else {
            order.quantity * order.price
        }
    }
}