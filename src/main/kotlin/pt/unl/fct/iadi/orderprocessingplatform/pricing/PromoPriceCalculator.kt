package pt.unl.fct.iadi.orderprocessingplatform.pricing

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import pt.unl.fct.iadi.orderprocessingplatform.domain.Order

@Component
@ConditionalOnProperty(prefix = "pricing.promo", name = ["enabled"], havingValue = "true")
class PromoPriceCalculator : PriceCalculator {
    override fun calculateTotalPrice(order: Order): Double {
        return order.items.sumOf { item ->
            var normalPrice = item.quantity * item.price
            if(item.quantity > 5) {
                normalPrice *= 0.8
            }
            normalPrice
        }
    }
}