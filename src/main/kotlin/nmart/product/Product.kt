package nmart.product

data class Product(
    val id: String,
    val name: String,
    private val description: String,
    private var price: Int,
    val category: ProductCategory,
    var available_item_count: Int = 0,

    val sellerAccounts: MutableList<Int> = mutableListOf()
) {
    val productPrice = price
}