package nmart.accounts

import nmart.utilClass
import nmart.controllers.MemberController
import nmart.utils.Account
import nmart.orders.Order
import nmart.orders.ShoppingCart
import nmart.product.ProductCategory
import nmart.controllers.ProductCategoryController
import nmart.controllers.updateProductCategoryInProductsDb
import nmart.utils.AccountStatus
import nmart.utils.Address

class Admin(
    override val accountId: Int,
    private val pcController: ProductCategoryController,
    private val memberController: MemberController
) : RegAccount {

    fun addProductCategory(newProductCategory: ProductCategory) {
        pcController.addCategory(newProductCategory)
        updateProductCategoryInProductsDb(newProductCategory)
    }

    fun createProductCategory() {
        val pc4 = ProductCategory(id = 4, name = "rings")
        // adding a dummy category
        pcController.addCategory(pc4)
    }

    fun getProductCategory(): ProductCategory {
        return ProductCategoryController.getProductCategory()
    }

    private fun getMemberToChangeStatus(): Account {
        with(memberController) {
            return utilClass.getItem(getAccounts())
        }
    }

    fun changeCustomerAccountStatus(status: AccountStatus) {
        // block or unblock a member
        getMemberToChangeStatus().accountStatus = status
    }

    fun createMember(guest: Guest? = null) {

        // adding a dummy account for simplicity
        val newAccount = Account(id = 4, password = "pass", name = "latha")

        // choose whether the new account is a buyer or a seller
        println(
            "1. buyer\n" +
            "2. seller"
        )
        // pretend here we collect the necessary documents to create an account

        val choice = utilClass.getChoice()

        var ordersList: MutableMap<Int, Order> = mutableMapOf()
        var cartObject = ShoppingCart(mutableListOf())
        guest?.apply {
            ordersList = orders
            cartObject = cart
        }
        val address = Address(street = "s3", city = "c3", state = "tn1", zip_code = "z3", country = "India")
        val member = if (choice == 1) {
            Buyer(cart = cartObject, orders = ordersList, accountId = newAccount.id, address = address)
        } else {
            Seller(newAccount.id, address)
        }
        memberController.addMember(newAccount, member)
    }
}