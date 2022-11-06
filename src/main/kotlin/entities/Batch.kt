package entities

import java.time.LocalDate

class Batch (
    val number: Int = 0,
    val flavor: String = "",
    val pourDate: LocalDate = LocalDate.now(),
    val cureDate: LocalDate =  LocalDate.now().plusWeeks(6),
    val recipe: Recipe? = null,
    val bars: ArrayList<Bar> = arrayListOf(),
) {
    fun cutIntoBars(barCount: Int) {
        (1..barCount).iterator().forEach {
            this.bars.add(Bar(batch = this))
        }
    }

    fun calculateCost(): Int {
        if (recipe == null) return 0
        return recipe.ingredients.fold(initial = 0) {
            acc, i -> acc + i.getCost()
        }
    }

    fun isCured(): Boolean {
        return cureDate.isEqual(LocalDate.now()) || cureDate.isBefore(LocalDate.now())
    }

    override fun toString(): String {
        if (recipe == null) return "Batch #$number: $flavor"
        return "Batch #$number: $flavor (${recipe.name} ${recipe.version})"
    }
}