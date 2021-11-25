package nmart.accounts

import nmart.utils.Address

sealed interface Member {
    val address: Address
}