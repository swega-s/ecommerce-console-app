package nmart.accounts

import nmart.controllers.ProductCategoryController
import nmart.controllers.ProductController
import nmart.product.Product
import nmart.utils.Address

class Seller(
    override val accountId: Int, override val address: Address
) : RegAccount, Member {

    companion object {
        val productsDb = ProductController()
    }

    // add a new product to the products db
    fun addProduct() {

        // enter details of the product you are about to sell
        // for simplicity a dummy product is created below
        val category = ProductCategoryController.getProductCategory()
        val newProduct = Product(
            id = "3", name = "Colgate", description = "this is paste",
            price = 20, category = category,
            available_item_count = 20
        ).also {
            it.sellerAccounts.add(this.accountId)
        }
        productsDb.addProduct(newProduct)
    }

}