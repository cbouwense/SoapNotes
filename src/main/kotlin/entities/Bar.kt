package entities

data class Bar(
    val id: Int = 0,
    val recipe: Recipe? = null,
    val isCured: Boolean = false,
    var owner: Person? = null,
)
