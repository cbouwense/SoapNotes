package entities

import java.util.*

class Recipe(
    val id: UUID = UUID.randomUUID(),
    val ingredients: ArrayList<Ingredient> = arrayListOf(),
    var name: String = "",
    var version: String = "",
) {
    fun addIngredient(i: Ingredient) {
        ingredients.add(i)
    }

    fun removeIngredient(i: Ingredient) {
        ingredients.remove(i)
    }
}
