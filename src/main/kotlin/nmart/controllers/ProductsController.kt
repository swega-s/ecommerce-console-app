package nmart.controllers

import nmart.accounts.Admin
import nmart.orders.Item
import nmart.product.Product
import nmart.product.ProductCategory

var productsInitialized = false
class ProductController {
    val products: Map<String, Product> = ProductsDb.products

    fun addProduct(newProduct: Product) {
        ProductsDb.addProduct(newProduct)
    }

    fun updateProductCount(items: List<Item>) {
        for (item in items) {
            ProductsDb.products[item.productName]
                ?.let {
                    it.available_item_count -= item.quantity
                }
        }
    }
}

fun Admin.updateProductCategoryInProductsDb(category: ProductCategory) {
    ProductsDb.updateProductCategoryInProducts(category.id, category.name)
}

private object ProductsDb {
    val products: MutableMap<String, Product> = mutableMapOf()

    init {
        if (!productsInitialized) {
            initializeProducts()
            productsInitialized = true
        }
    }

    private fun initializeProducts() {
        val product1 = Product(id = "p1", name = "rise", description = "this is rise", price = 100,
                                category = ProductCategory(id = 1, "soap"), available_item_count = 100)
        val product2 = Product(id = "p2", name = "rise", description = "this is rise", price = 100,
            category = ProductCategory(id = 2, "soap"), available_item_count = 100)
        val product3 = Product(id = "p3", name = "dabbar", description = "this is dabbar", price = 100,
            category = ProductCategory(id = 3, "tooth paste"), available_item_count = 100)
        addProduct(product1)
        addProduct(product2)
        addProduct(product3)
    }

    fun addProduct(newProduct: Product) {
        val productName = newProduct.name.lowercase()
        if (products.containsKey(productName)) {
            products[productName]!!.available_item_count += newProduct.available_item_count
        } else {
            products[productName] = newProduct
        }
    }

    fun updateProductCategoryInProducts(productCategoryId: Int, newName: String) {
        products.onEach {
            if (it.value.category.id == productCategoryId) {
                it.value.category.name = newName
            }
        }
    }
}
