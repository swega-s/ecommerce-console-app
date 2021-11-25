package nmart.utils

data class Account(
    val id: Int,
    var password: String,
    val name: String,
    var accountStatus: AccountStatus = AccountStatus.ACTIVE
)