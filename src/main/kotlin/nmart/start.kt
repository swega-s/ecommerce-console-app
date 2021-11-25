package nmart

import nmart.authenticate.Log
import nmart.controllers.ProductController
import nmart.controllers.MemberController
import nmart.orders.Item
import nmart.orders.ShoppingCart
import nmart.search.Catalog
import nmart.accounts.*
import nmart.utils.AccountStatus
import nmart.utils.ShipmentStatus
import nmart.utils.UtilClass
import kotlin.system.exitProcess

private lateinit var memberController: MemberController
val utilClass = UtilClass()

fun main() {

    println("Welcome!")
    val products = ProductController() // products database
    memberController = MemberController()
    val login = Log(memberController)
    val catalog = Catalog(products.products)

    // choosing the usertype
    // consider this as the stage when you open an ecommerce app and choosing which user you are!

    while (true) {
        println("1. Guest\n2. Registered account( Buyer/ Seller/ Admin)\n3. exit")
        print("Choose the usertype:\n")

        when (utilClass.getChoice()) {
            1    -> {
                // a guest
                val guest = Guest(cart = ShoppingCart(mutableListOf()), orders = mutableMapOf())
                performCustomerOptions(guest, catalog, products)
            }
            2    -> {
                when (val user = login.getValidatedAccount()) {
                    is Buyer -> repeatUntilBreak {
                        performCustomerOptions(user, catalog, products)
                    }
                    is Admin  -> repeatUntilBreak {
                        performAdminOptions(user)
                    }
                    is Seller -> repeatUntilBreak {
                        performSellerOptions(user)
                    }
                }
            }
            else -> {
                println("thank you!")
                exitProcess(0)
            }
        }
    }
}

private inline fun repeatUntilBreak(func: () -> Boolean) {
    var repeat: Boolean
    do {
        repeat = func()
    } while (repeat)
}

private fun getCustomerOption(): Int {
    println(
        "-----------------------------------\n" +
                "1. search in catalog\n" +
                "2. see cart\n" +
                "3. see my orders\n" +
                "4. sell on imart\n" +
                "5. change password\n" +
                "6. Logout\n" +
                "-----------------------------------"
    )

    // get a choice from the customer
    return utilClass.getChoice()
}

private fun getSellerOptions(): Int {
    println(
        "-----------------------------------\n" +
                "1. Add product\n" +
                "2. Change password\n" +
                "3. Logout\n" +
                "-----------------------------------"
    )
    // get a choice from the seller
    return utilClass.getChoice()
}

private fun getAdminOptions(): Int {
    println(
        "-----------------------------------\n" +
                "1. create a new buyer/ seller account\n" +
                "2. add a product category\n" +
                "3. modify a product category\n" +
                "4. block a member\n" +
                "5. unblock a member\n" +
                "6. change password\n" +
                "7. logout\n" +
                "-----------------------------------"
    )

    // get a choice from the admin
    return utilClass.getChoice()
}

private fun performAdminOptions(admin: Admin): Boolean {
    var stayLoggedIn = true

    return with(admin) {
        // get admin choice
        when (getAdminOptions()) {
            1    -> createMember() // create a new account who is not a guest yet
            2    -> {
                // adding a new product category (to where?)
                createProductCategory()
            }
            3    -> {
                // modifying a product category
                val itemToModify = getProductCategory()

                // for simplicity, we perform modification as converting name to uppercase
                itemToModify.name = itemToModify.name.uppercase()
                addProductCategory(itemToModify) // sending a category to get modified
            }
            4    -> {
                // blocking a registered account
                changeCustomerAccountStatus(AccountStatus.BLOCKED)
            }
            5    -> {
                // unblocking a registered account
                changeCustomerAccountStatus(AccountStatus.ACTIVE)
            }
            6    -> {
                changePassword(memberController)
            }
            else -> stayLoggedIn = false
        }
        stayLoggedIn
    }
}

private fun performSellerOptions(seller: Seller): Boolean {
    var stayLoggedIn = false
    return with(seller) {
        when (getSellerOptions()) {
            1    -> {
                addProduct()
                stayLoggedIn = true
            }
            2    -> {
                changePassword(memberController)
                println("please enter your credentials again!")
            }
        }
        stayLoggedIn
    }
}

private fun performCustomerOptions(
    customer: Customer,
    catalog: Catalog,
    products: ProductController
): Boolean {
    with(customer) {
        return when (getCustomerOption()) {
            1    -> {
                if (searchInCatalog(catalog)) {
                    isBuyerToPlaceOrderCheck(customer, products)
                }
                customer !is Guest
            }
            2    -> {
                printMyCartItems()
                if (customer.cart.getItems().isNotEmpty()) {
                    println(
                        "-----------------------------------\n" +
                                "1. edit the cart?\n" +
                                "2. place order\n" +
                                "3. go back\n" +
                                "-----------------------------------"
                    )
                    when (utilClass.getChoice()) {
                        1 -> {
                            println("choose item to edit: ")
                            printMyCartItems()
                            // choice should be within size
                            var itemChoice = utilClass.getChoice()
                            while (!(itemChoice > 0 && itemChoice <= cart.getItems().size)) {
                                itemChoice = utilClass.getChoice()
                            }

                            val item = cart.getItems()[itemChoice - 1]
                            println(
                                "1. remove item\n" +
                                        "2. edit quantity"
                            )
                            val option = utilClass.getChoice()
                            if (option == 1) {
                                cart.removeItemFromCart(item)
                            } else {
                                println(
                                    "enter the quantity to be added\n" +
                                            "current quantity - ${item.quantity}"
                                )
                                val editableQuantity = utilClass.getChoice()
                                if (editableQuantity < 0) {
                                    if (canEditQuantity(item, editableQuantity)) {
                                        item.quantity += editableQuantity
                                        if (item.quantity == 0) {
                                            cart.removeItemFromCart(item)
                                        }
                                    } else {
                                        println("you cannot remove more items than the actual item quantity")
                                    }
                                } else {
                                    item.quantity += editableQuantity
                                }
                            }
                            isBuyerToPlaceOrderCheck(customer, products)
                        }
                        2 -> {
                        // placing the order
                        isBuyerToPlaceOrderCheck(customer, products)
                        }
                        else -> {
                            println("your items in the order is unchanged!")
                        }
                    }
                } else {
                    println("No item in your cart")
                }
                true
            }
            3    -> {
                // see my orders
                if (customer !is Guest) {
//                    for ((index, order) in customer.orders) {
//                        println("\n${index + 1}")
//                        order.items.forEach { println("${it.productName} -> ${it.quantity}") }
//                    }
                    customer.orders.forEach {
                        println("Order number: ${it.key}")
                        it.value.items.forEach { item ->
                            println("${item.productName} -> ${item.quantity}")
                        }
                    }
                    if (orders.isNotEmpty()) {
                        // cancelling an order
                        println(
                            "1. cancel an order\n" +
                                    "2. go back"
                        )
                        if (utilClass.getChoice() == 1) {
                            // assume here we see all orders present
                            println("enter order number to cancel:")
                            var choice = utilClass.getChoice()
                            while (choice !in orders.keys) {
                                println("enter a valid order number")
                                choice = utilClass.getChoice()
                            }
                            val order = orders[choice]!!
                            if (order.shipmentStatus == ShipmentStatus.PENDING) {
                                order.items.forEach {
                                    it.quantity = -it.quantity
                                    products.updateProductCount(order.items)
                                }
                                println(order.items.toList())
                                orders.remove(order.orderNumber)
                            } else {
                                println("Order is already shipped. Can't cancel the order now!")
                            }
                        }
                    }
                }
                true
            }
            4    -> {
                var stayLoggedIn = true
                if (customer is Guest) {
                    memberController.createMember(customer)
                } else {
                    println(
                        "would you like to create a new seller account?\n" +
                                "1. yes\n" +
                                "2. no"
                    )
                    if (utilClass.getChoice() == 1) {
                        memberController.createMember()
                    }
                    stayLoggedIn = false
                }
                stayLoggedIn
            }
            5    -> {
                var stayLoggedIn = true
                if (customer is RegAccount) {
                    customer.changePassword(memberController)
                } else {
                    print("You don't have an account. Create an account now!")
                    memberController.createMember()
                    stayLoggedIn = false
                }
                stayLoggedIn
            }
            else -> false // going to log in again
        }
    }
}

private fun canEditQuantity(item: Item, quantity: Int) = -quantity <= item.quantity

private fun isBuyerToPlaceOrderCheck(customer: Customer, products: ProductController) {
    if (customer is Buyer) {
        val items = customer.cart.getItems()
        if (items.isNotEmpty()) {
            println("order summary")
            items.forEachIndexed { index, item ->
                if (item.quantity > products.products[item.productName]!!.available_item_count) {
                    println("not enough quantity available for your purchase!")
                } else {
                    println("${index + 1}. ${item.productName} : Ordered quantity - ${item.quantity}")
                }
            }
            with(customer) {
                if (buyingNow()) {
                    products.updateProductCount(cart.getItems())
                }
            }
        } else {
            println("no products found in the cart")
        }
        println("-----------------------------------")
    } else if (customer is Guest) {
        println(
            "sign up to an account?\n" +
                    "or you will lose your cart details...\n" +
                    "1. yes 2. no"
        )
        if (utilClass.getChoice() == 1) {
            memberController.createMember(customer)
        } else {
            println("bye guest!")
        }
    }
}