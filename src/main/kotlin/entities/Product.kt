package entities

import java.util.*

class Product(
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    val netWeightAmount: Float = 0.0f,
    val netWeightUnit: MeasurementUnit = MeasurementUnit.OUNCES,
    val priceInCents: Int = 0,
) {

}
