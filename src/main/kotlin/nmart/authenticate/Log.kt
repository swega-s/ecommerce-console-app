package nmart.authenticate

import nmart.utilClass
import nmart.accounts.RegAccount
import nmart.controllers.MemberController
import nmart.utils.Account
import nmart.utils.AccountStatus

class Log(private val memberController: MemberController) {
    private val accounts: Map<String, Account> = memberController.getAccounts()
    private val members: Map<Int, RegAccount> = memberController.getMembers()

    private fun validateUser(username: String, pass: String): RegAccount? {
        return if (username in accounts.keys) {
            accounts[username]?.run {
                if (accountStatus == AccountStatus.ACTIVE && password == pass) {
                    // valid user
                    members[id]
                } else {
                    println("wrong password! try again!")
                    null
                }
            }
        } else {
            println("no account found! create an account!")
            memberController.createMember()
            null
        }
    }

    fun login(): RegAccount {
        print("Enter username: ")
        val username = utilClass.getStringInput()// read username
        print("Enter password: ")
        val password = utilClass.getStringInput() // read password
        return validateUser(username, password) ?: login()
    }
}