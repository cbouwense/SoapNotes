package entities

import java.util.*

data class Bar(
    val id: UUID = UUID.randomUUID(),
    val recipe: Recipe,
    var isCured: Boolean = false,
    var owner: Person? = null,
)
