package entities

data class Ingredient(
    val name: String = "",
    val product: Product? = null,
    var measurementAmount: Float = 0.0f,
    var measurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,
)
