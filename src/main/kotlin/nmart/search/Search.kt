package nmart.search

import nmart.product.Product

// a general interface for mentioning search categories
// this provides an abstract layer

interface Search {
    fun searchProductsByName(name: String): List<Product>

    fun searchProductsByCategory(category: String): List<Product>
}