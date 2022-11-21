package entities

import java.time.*

class Batch (
    val id: Int = 0,
    val name: String = "",
    val recipe: Recipe? = null,
    val bars: ArrayList<Bar> = arrayListOf(),
    val pourDate: Int = 0,
    val cureDate: Int = 0,
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
        return (
            cureDate == LocalDate.now().toEpochSecond(LocalTime.now(), ZoneOffset.UTC).toInt() ||
            cureDate < LocalDate.now().toEpochSecond(LocalTime.now(), ZoneOffset.UTC).toInt()
        )
    }

    override fun toString(): String {
        if (recipe == null) return "Batch #$id: $name"
        return "Batch #$id: $name (${recipe.name} ${recipe.version})"
    }
}