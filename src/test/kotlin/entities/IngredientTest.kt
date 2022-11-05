package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class IngredientTest {
    @Test
    fun `given a name, it should be initialized to that name`() {
        val ingredient = Ingredient(name = "olive oil")

        assertEquals("olive oil", ingredient.name)
    }

    @Test
    fun `given a measurement amount, it should be initialized to that amount`() {
        val ingredient = Ingredient(measurementAmount = 4.2f)

        assertEquals(4.2f, ingredient.measurementAmount)
    }

    @Test
    fun `it should be able to change the measurement amount`() {
        val ingredient = Ingredient(measurementAmount = 4.2f)

        ingredient.measurementAmount = 13.37f

        assertEquals(13.37f, ingredient.measurementAmount)
    }

    @Test
    fun `given a measurement unit, it should be initialized to that unit`() {
        val ingredient = Ingredient(measurementUnit = MeasurementUnit.GRAMS)

        assertEquals(MeasurementUnit.GRAMS, ingredient.measurementUnit)
    }

    @Test
    fun `it should be able to change the measurement unit`() {
        val ingredient = Ingredient(measurementUnit = MeasurementUnit.GRAMS)

        ingredient.measurementUnit = MeasurementUnit.MILLILITERS

        assertEquals(MeasurementUnit.MILLILITERS, ingredient.measurementUnit)
    }

    @Nested
    inner class GetCost {
        // $1 per gram.
        private val rednersOliveOil = Product(
            netWeightAmount = 100.0f,
            netWeightUnit = MeasurementUnit.GRAMS,
            priceInCents = 10000,
        )

        @Test
        fun `when the ingredient doesn't have an associated product, then it should return 0`() {
            val ingredient = Ingredient()

            assertEquals(0, ingredient.getCost())
        }

        @Test
        fun `when the amount of the ingredient is 0, then it should return 0`() {
            val ingredient = Ingredient(product = rednersOliveOil)

            assertEquals(0, ingredient.getCost())
        }

        @Test
        fun `it should return the cost of the product`() {
            // 10 grams of olive oil at $1/g = $10
            val oliveOil = Ingredient(
                product = rednersOliveOil,
                measurementAmount = 10.0f,
                measurementUnit = MeasurementUnit.GRAMS,
            )

            assertEquals(1000, oliveOil.getCost())
        }
    }
}