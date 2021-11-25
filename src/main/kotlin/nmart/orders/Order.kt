package nmart.orders

import nmart.utilClass
import nmart.utils.OrderStatus
import java.time.Instant
import java.util.*

class Order(
    val orderNumber: Int = order++,
    val items: List<Item>,
    var orderStatus: OrderStatus = OrderStatus.PENDING,
    private val orderDate: Date = Date.from(Instant.now())
) {

    companion object {
        private var order = 1
    }

    fun makePayment(): Boolean { // should add details about payment in argument

        println("Total amount to pay: ${
            utilClass.getAmount(items) { quantity: Int, price: Int -> quantity * price }
        }")
        println("Confirm payment?\n" +
                "1. yes\n" +
                "2. no")
        return if (utilClass.getChoice() == 1) {
            println("payment successful. your order is ready to ship!")
            true
        } else {
            false
        }
    }
}