package nmart.accounts

import nmart.orders.Order
import nmart.orders.ShoppingCart

// general candidate who can browse items without an account

class Guest(cart: ShoppingCart,
            orders: MutableMap<Int, Order>
) : Customer(cart, orders)
