package entities

import java.time.LocalDate

class Batch (
    val number: Int = 0,
    var name: String = "",
    val flavor: String = "",
    val pourDate: LocalDate = LocalDate.now(),
    val cureDate: LocalDate =  LocalDate.now().plusWeeks(6),
    val recipe: Recipe? = null,
) {
    fun isCured(): Boolean {
        return cureDate.isEqual(LocalDate.now()) || cureDate.isBefore(LocalDate.now())
    }

    override fun toString(): String {
        if (recipe == null) return "Batch #$number: $flavor"
        return "Batch #$number: $flavor ($recipe.name $recipe.version)"
    }
}