package nmart.accounts

import nmart.utilClass
import nmart.orders.Order
import nmart.orders.ShoppingCart
import nmart.utils.Address

class Buyer(
    override val accountId: Int,
    cart: ShoppingCart,
    orders: MutableMap<Int, Order>, override val address: Address
) : RegAccount, Customer(cart, orders), Member {

    fun buyingNow(): Boolean {
        println("Buy now? press 1 else press any other number")
        return if (utilClass.getChoice() == 1) {
            val order = Order(items = cart.getItems())
            placeOrder(order)
        } else {
            println("order placement incomplete. Your products remain in the cart!")
            false
        }
    }

    private fun placeOrder(order: Order): Boolean {
        return if (makePayment()) {
            orders[order.orderNumber] = order
            clearItemsFromCart()
            viewMyOrderedItems()
            true
        } else {
            println("payment incomplete!")
            false
        }
    }

    private fun viewMyOrderedItems() {
        orders.forEach {
            it.value.items.forEach { item ->
                println("${it.key}. ${item.productName} -> Ordered quantity: ${item.quantity}")
            }
        }
    }

    private fun clearItemsFromCart() = cart.checkout()

    private fun makePayment(): Boolean { // should add details about payment in argument

        println("Total amount to pay: ${
            utilClass.getAmount(cart.getItems()) { quantity: Int, price: Int -> quantity * price }
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