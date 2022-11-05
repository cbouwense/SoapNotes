package entities

import java.time.LocalDate

class Batch (
    val number: Int = 0,
    val flavor: String = "",
    val pourDate: LocalDate = LocalDate.now(),
    val cureDate: LocalDate =  LocalDate.now().plusWeeks(6),
    val recipe: Recipe? = null,
) {
    fun calculateCost(): Int {
//        return recipe.ingredients.reduce {
//            acc: Ingredient, i: Ingredient -> acc += i.getCost()
//        }
        return 0
    }

    fun isCured(): Boolean {
        return cureDate.isEqual(LocalDate.now()) || cureDate.isBefore(LocalDate.now())
    }

    override fun toString(): String {
        if (recipe == null) return "Batch #$number: $flavor"
        return "Batch #$number: $flavor (${recipe.name} ${recipe.version})"
    }
}