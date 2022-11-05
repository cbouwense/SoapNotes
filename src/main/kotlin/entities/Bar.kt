package entities

import java.util.*

data class Bar(
    val id: UUID = UUID.randomUUID(),
    val batch: Batch? = null,
    var isCured: Boolean = false,
    var owner: Person? = null,
)
