package entities

import java.util.*

class Batch (
    val id: UUID = UUID.randomUUID(),
    val number: Int = 0,
    var name: String = "",
    val recipe: Recipe? = null,
) {
    override fun toString(): String {
        if (recipe == null) return "Batch #${number}"
        return "Batch #${number}: ${recipe.name} ${recipe.version}"
    }
}