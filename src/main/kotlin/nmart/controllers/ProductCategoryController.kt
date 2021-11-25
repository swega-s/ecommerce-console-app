package nmart.controllers

import nmart.product.ProductCategory
import nmart.utilClass

var categoriesInitialized = false

// controller which handles the product category db

class ProductCategoryController {

    fun addCategory(productCategory: ProductCategory) {
        ProductCategoryDB.addProductCategory(productCategory)
    }

    companion object {
        fun getProductCategory(): ProductCategory {
            return utilClass.getItem(ProductCategoryDB.productsCategories)
        }
    }

}

private object ProductCategoryDB {
    val productsCategories: MutableMap<Int, ProductCategory> = mutableMapOf()

    init {
        if (!categoriesInitialized) {
            addInitialProductCategories()
            categoriesInitialized = true
        }
    }

    fun addProductCategory(newProductCategory: ProductCategory) {
        if (newProductCategory.id !in productsCategories.keys) {
            productsCategories[newProductCategory.id] = newProductCategory
        }
//        println(productsCategories.onEach { println(it.value.name) })
    }

    fun addInitialProductCategories() {
        val pc0 = ProductCategory(id = 1, name = "shampoo")
        val pc1 = ProductCategory(id = 2, name = "soap")
        val pc2 = ProductCategory(id = 3, name = "tooth paste")
        addProductCategory(pc0)
        addProductCategory(pc1)
        addProductCategory(pc2)
    }
}