package use_cases

import entities.Ingredient
import entities.Recipe
import use_cases.ports.Persistence

class CreateRecipeUseCase {
    private val persistence: Persistence;

    operator fun invoke(name: String, version: String, ingredients: ArrayList<Ingredient>) {
        val recipe = Recipe(name = name, version = version, ingredients = ingredients)
        persistence.save(recipe = recipe)
    }
}