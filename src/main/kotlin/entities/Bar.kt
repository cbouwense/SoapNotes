package entities

import java.util.*

data class Bar(
    val id: UUID = UUID.randomUUID(),
    val recipe: Recipe? = null,
    var isCured: Boolean = false,
    var owner: Person? = null,
)
