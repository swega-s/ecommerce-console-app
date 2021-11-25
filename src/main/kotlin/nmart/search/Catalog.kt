package nmart.search

import nmart.product.Product

// a catalog or menu class which displays products available
class Catalog(
    private var products: Map<String, Product>
) : Search {

    // return products by comparing name
    override fun searchProductsByName(name: String): List<Product> {
        return products.filterKeys {
            it.contains(name.lowercase())
        }.values.toList()
    }

    // return products comparing by product category name
    override fun searchProductsByCategory(category: String): List<Product> {
        return products.filterValues {
            it.category.name.contains(category.lowercase())
        }.values.toList()
    }

    // general search which returns all products available
    fun search(): List<Product> {
        return products.values.toList()
    }
}