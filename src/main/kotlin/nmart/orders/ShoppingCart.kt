package nmart.orders

class ShoppingCart(private val items: MutableList<Item>) {
    fun addItemInCart(item: Item) {
        val presentItem = items.find { it.productID == item.productID }
        if (presentItem != null) {
            presentItem.quantity += item.quantity
        } else {
            items.add(item)
        }
    }

    fun getItems(): List<Item> {
        return items.toList()
    }

    fun removeItemFromCart(item: Item) {
        items.remove(item)
    }

    fun checkout() {
        items.clear()
    }
}