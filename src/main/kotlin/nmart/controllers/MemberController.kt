package nmart.controllers

import nmart.orders.ShoppingCart
import nmart.utils.Account
import nmart.utils.Address
import nmart.accounts.*

private var itemsInitialized = false

class MemberController {
    private val members = MemDb.members
    private val accounts = MemDb.accounts
    private val admin by lazy {
        members[accounts["admin"]?.id] as Admin
    }

    fun getAccounts(): MutableMap<String, Account> {
        return accounts
    }

    fun getMembers(): MutableMap<Int, RegAccount> {
        return members
    }

    fun addMember(account: Account, member: RegAccount) {
        MemDb.addMember(account.name, account, member)
    }

    fun createMember(guest: Guest? = null) {
        admin.createMember(guest)
    }
}

private object MemDb {
    private var memberNo: Int = 1
    val members: MutableMap<Int, RegAccount> = mutableMapOf()
    val accounts: MutableMap<String, Account> = mutableMapOf()

    init {
        if (!itemsInitialized) {
            addInitialItems()
            itemsInitialized = true
        }
    }

    // dummy data
    fun addInitialItems() {
        val account1 = Account(1, "pass", "swega")
        val account2 = Account(2, "pass", "harini")
        val account3 = Account(3, "pass", "admin")

        accounts["swega"] = account1
        accounts["harini"] = account2
        accounts["admin"] = account3

        val address1 = Address(street = "s1", city = "c1", state = "tn1", zip_code = "z1", country = "India")
        val address2 = Address(street = "s2", city = "c2", state = "tn1", zip_code = "z2", country = "India")

        val member1 = Buyer(account1.id, ShoppingCart(mutableListOf()), mutableMapOf(), address = address1)
        val member2 = Seller(account2.id, address2)
        val admin = Admin(account3.id, ProductCategoryController(), MemberController())

        members[memberNo++] = member1
        members[memberNo++] = member2
        members[memberNo++] = admin
    }

    fun addMember(accKey: String, account: Account, regAccount: RegAccount) {
        accounts[accKey] = account
        members[memberNo++] = regAccount
    }
}
