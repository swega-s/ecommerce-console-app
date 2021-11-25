package nmart.orders

import nmart.utils.ShipmentStatus
import java.time.Instant
import java.util.*

class Order(
    val orderNumber: Int = order++,
    val items: List<Item>,
    var shipmentStatus: ShipmentStatus = ShipmentStatus.PENDING,
    private val orderDate: Date = Date.from(Instant.now())
) {

    companion object {
        private var order = 1
    }
}