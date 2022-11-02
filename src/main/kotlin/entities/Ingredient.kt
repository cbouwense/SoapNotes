package entities

data class Ingredient(
    val name: String = "",
    val priceInCents: Int = 0,
    val measurementAmount: Float = 0.0f,
    val measurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,
)