package use_cases.ports

import entities.Recipe

interface Persistence {
    fun create(table: String, recipe: Recipe)
    fun getById(table: String, id: Int): Recipe
}