package use_cases

import entities.Recipe
import ports.RecipeRepo

class CreateRecipe(val recipe: Recipe, val recipeRepo: RecipeRepo) {
    fun run(): Int {
        return recipeRepo.create(r = recipe)
    }
}