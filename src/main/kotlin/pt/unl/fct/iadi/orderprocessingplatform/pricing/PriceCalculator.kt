package pt.unl.fct.iadi.orderprocessingplatform.pricing

import pt.unl.fct.iadi.orderprocessingplatform.domain.Order
import pt.unl.fct.iadi.orderprocessingplatform.domain.OrderItem

interface PriceCalculator {
    fun calculateTotalPrice(order: OrderItem): Double
}