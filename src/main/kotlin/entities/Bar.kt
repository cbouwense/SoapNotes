package entities

enum class Flavor {
    UNSCENTED,
    COFFEE,
}

data class Bar(
    val id: Int = 0,
    val flavor: Flavor = Flavor.UNSCENTED,
    val isCured: Boolean = false,
    var owner: Person? = null,
)

data class Person(
    val name: String = "Rachel"
)