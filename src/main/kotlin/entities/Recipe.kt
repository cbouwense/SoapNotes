package entities

import java.util.*

data class Recipe(
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var version: String = "",
)
