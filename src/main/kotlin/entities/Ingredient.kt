package entities

import kotlin.math.roundToInt

class Ingredient(
    val product: Product? = null,
    var measurementAmount: Float = 0.0f,
    var measurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,
) {
    fun getCost(): Int {
        if (product == null) return 0
        return (product.getCentsPerGram() * this.amountInGrams()).roundToInt()
    }

    private fun amountInGrams(): Float {
        when (this.measurementUnit) {
            MeasurementUnit.OUNCES -> return this.measurementAmount * 28.34952f
            else -> return this.measurementAmount
        }
    }
}
