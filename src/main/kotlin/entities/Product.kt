package entities

import java.util.*

class Product(
    val id: Int = 0,
    var name: String = "",
    val netWeightAmount: Float = 0.0f,
    val netWeightUnit: MeasurementUnit = MeasurementUnit.GRAMS,
    val priceInCents: Int = 0,
) {
    fun getCentsPerGram(): Int {
        if (netWeightAmount == 0.0f) throw ArithmeticException("Attempted division by zero.");
        return (priceInCents / convertWeightToGrams(this.netWeightUnit)).toInt()
    }

    private fun convertWeightToGrams(units: MeasurementUnit): Float {
        when (units) {
            MeasurementUnit.OUNCES -> return this.netWeightAmount * 28.34952f
            else -> return this.netWeightAmount
        }
    }
}
