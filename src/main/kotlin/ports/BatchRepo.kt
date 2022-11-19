package ports

import entities.Recipe

interface BatchRepo {
    fun create(recipe: Recipe): Int
    fun findById(id: Int): Recipe?
    fun findByName(name: String): Recipe?
}