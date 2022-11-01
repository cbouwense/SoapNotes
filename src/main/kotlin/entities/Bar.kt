package entities

data class Bar(
    val id: Int = 0,
    val flavor: Flavor = Flavor.UNSCENTED,
    val isCured: Boolean = false,
    var owner: Person? = null,
)
