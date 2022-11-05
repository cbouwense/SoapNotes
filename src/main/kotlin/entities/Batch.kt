package entities

import java.util.*

class Batch (
    val id: UUID = UUID.randomUUID(),
    val number: Int = 0,
    var name: String = "",
    val recipe: Recipe? = null,
    val flavor: String = "",
) {
    override fun toString(): String {
        if (recipe == null) return "Batch #${number}"
        return "Batch #${number}: ${flavor} (${recipe.name} ${recipe.version})"
    }
}