package ports

import entities.Recipe

interface RecipeRepo {
    fun create(r: Recipe): Int
    fun findById(id: Int): Recipe?
    fun findByNameAndVersion(name: String, version: String): Recipe?
    fun getAll(): List<Recipe>
    fun getMaxId(): Int
    // TODO
    // fun findLatestByName(name: String): Recipe?
}