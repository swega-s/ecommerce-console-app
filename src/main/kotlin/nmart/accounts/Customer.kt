package nmart.accounts

import nmart.utilClass
import nmart.orders.Item
import nmart.orders.Order
import nmart.orders.ShoppingCart
import nmart.product.Product
import nmart.search.Catalog

// customer
sealed class Customer(
    val cart: ShoppingCart,
    val orders: MutableMap<Int, Order>
) {

    fun printMyCartItems() {
        cart.getItems().forEachIndexed { index, item ->
            println("${index + 1}. ${item.productName} -> Added quantity: ${item.quantity}")
        }
    }

    fun searchInCatalog(catalog: Catalog): Boolean {
        // search by name or category

        println(
            "1. search by name\n" +
                    "2. search by category\n" +
                    "3. general search (all products)"
        )

        val resultProducts = when (utilClass.getChoice()) {
            1    -> {
                print("Enter a name: ")
                val name = utilClass.getStringInput()
                catalog.searchProductsByName(name)
            }
            2    -> {
                print("Enter a category: ")
                val category = utilClass.getStringInput()
                catalog.searchProductsByCategory(category)
            }
            else -> {
                println("Showing all products")
                catalog.search()
            }
        }

        return if (resultProducts.isNotEmpty()) {
            resultProducts.forEachIndexed { index, product ->
                println(
                    "${index + 1}. ${product.name} " +
                            "-> Category: ${product.category.name} " +
                            "-> Rs. ${product._price}"
                )
            }
            return chooseProductToAddToCart(resultProducts)
        } else {
            println("no products found with the given data!")
            false
        }
    }

    private fun chooseProductToAddToCart(products: List<Product>): Boolean {
        val choice = utilClass.getChoice()
        if (choice < 0 || choice > products.size) {
            return false
        } else {
            print("Enter required quantity: \n")
            val requiredQuantity = utilClass.getChoice()
            val product = products[choice - 1]
            if (product.available_item_count < requiredQuantity) {
                println("not enough quantity is available for you to buy!")
            } else {
                this.cart.addItemInCart(createAndReturnItem(product, requiredQuantity))
            }
        }
        return true
    }

    private fun createAndReturnItem(product: Product, requiredQuantity: Int): Item {
        return Item(
            productName = product.name,
            productID = product.id,
            quantity = requiredQuantity,
            price = product._price
        )
    }
}