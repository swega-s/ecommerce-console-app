package nmart.search

import nmart.product.Product

interface Search {
    fun searchProductsByName(name: String): List<Product>

    fun searchProductsByCategory(category: String): List<Product>
}