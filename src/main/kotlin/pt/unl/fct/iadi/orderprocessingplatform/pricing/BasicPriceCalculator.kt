package pt.unl.fct.iadi.orderprocessingplatform.pricing

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import pt.unl.fct.iadi.orderprocessingplatform.domain.OrderItem

@Component
@ConditionalOnProperty(prefix = "pricing.promo", name = ["enabled"], havingValue = "false", matchIfMissing = true)
class BasicPriceCalculator: PriceCalculator {
    override fun calculateTotalPrice(order: OrderItem): Double =
        order.quantity * order.price
}