package entities

data class Ingredient(
    val name: String = "",
    var priceInCents: Int = 0,
    var measurementAmount: Float = 0.0f,
    var measurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,
)
