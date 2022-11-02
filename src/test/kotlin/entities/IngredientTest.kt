package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IngredientTest {
    @Test
    fun `given a name, it should be initialized to that name`() {
        val ingredient = Ingredient(name = "olive oil")

        assertEquals("olive oil", ingredient.name)
    }

    @Test
    fun `given a price, it should be initialized to that price`() {
        val ingredient = Ingredient(priceInCents = 1337)

        assertEquals(1337, ingredient.priceInCents)
    }

    @Test
    fun `given a measurement amount, it should be initialized to that amount`() {
        val ingredient = Ingredient(measurementAmount = 4.2f)

        assertEquals(4.2f, ingredient.measurementAmount)
    }

    @Test
    fun `given a measurement unit, it should be initialized to that unit`() {
        val ingredient = Ingredient(measurementUnit = MeasurementUnit.GRAMS)

        assertEquals(MeasurementUnit.GRAMS, ingredient.measurementUnit)
    }
}