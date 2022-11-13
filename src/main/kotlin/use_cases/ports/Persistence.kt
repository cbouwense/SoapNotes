package use_cases.ports

import entities.Recipe
import java.util.UUID

interface Persistence {
    fun getById(id: UUID)
    fun save(recipe: Recipe)
}