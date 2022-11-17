package use_cases.ports

import entities.Recipe

interface Persistence {
    fun create(table: String, values: List<String>)
    fun getById(table: String, id: Int): Recipe
}