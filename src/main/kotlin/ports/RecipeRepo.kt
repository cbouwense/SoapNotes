package ports

import entities.Recipe

interface RecipeRepo {
    fun create(recipe: Recipe): Int
    fun findById(id: Int): Recipe?
    fun findByNameAndVersion(name: String, version: String): Recipe?
    fun getAll(): List<Recipe>
    // TODO
    // fun findLatestByName(name: String): Recipe?
}