package nmart.utils

// all buyers and sellers will have an address for shipment process
data class Address(
    private val street: String,
    private val city: String,
    private val state: String,
    private val zip_code: String,
    private val country: String
)