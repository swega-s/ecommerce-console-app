package nmart.utils

import nmart.orders.Item
import nmart.product.ProductCategory
import java.lang.Exception

// Utilclass -> a helper class
class UtilClass {

    // getitem - function overloaded based on the function arguments
    fun getItem(itemMap: Map<String, Account>): Account {
        println("ENTER USERNAME")
        itemMap.onEachIndexed {
                index, entry -> println("${index + 1}. ${entry.value.name}")
        }
        var choice = getStringInput()
        while (!(itemMap.containsKey(choice))) {
            print("key not present. enter valid key: ")
            choice = getStringInput()
        }
        return itemMap[choice]!!
    }

    fun getItem(itemMap:  Map<Int, ProductCategory>): ProductCategory {
        itemMap.onEach {
                entry -> println("${entry.key}-> ${entry.value.name}")
        }
        var choice = getChoice()
        while (!(itemMap.containsKey(choice))) {
            print("key not present. enter valid key: ")
            choice = getChoice()
        }
        return itemMap[choice]!!
    }

    // getting an integer input from the user
    fun getChoice(): Int {
        startOver@ while (true) {
            try {
                print("Enter you choice: ")
                return readLine()!!.toInt()
            } catch (exc: Exception) {
                println("please enter a valid value!")
                continue@startOver
            }
        }
    }

    // getting a string input from the user
    fun getStringInput(): String {
        startOver@ while (true) {
            try {
                return readLine()!!
            } catch (exc: Exception) {
                println("please enter a valid value!")
                continue@startOver
            }
        }
    }

    // calculate total amount to pay
    fun getAmount(items: List<Item>, func: (data1: Int, data2: Int) -> Int): Int {
        var amount = 0
        items.forEach {
            amount += func(it.quantity, it.price)
        }
        return amount
    }
}