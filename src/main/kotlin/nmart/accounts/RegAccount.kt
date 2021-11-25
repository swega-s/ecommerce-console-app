package nmart.accounts

import nmart.utilClass
import nmart.controllers.MemberController

sealed interface RegAccount {
    val accountId: Int

    // find account using account id and then uncomment this
    fun changePassword(memberController: MemberController) {
        print("Enter your username here: ")
        val account = memberController.getAccounts()[utilClass.getStringInput()]
        if (account != null && account.id == this.accountId) {
            print("Enter old password: ")
            if (utilClass.getStringInput() == account.password) {
                print("Enter new password: ")
                account.password = utilClass.getStringInput()
            } else {
                println("wrong password! try again some other time!")
            }
        } else {
            println("username don't exist. try again!")
            changePassword(memberController)
        }
    }
}