package entities

import entities.MeasurementUnit.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

import java.time.LocalDate

internal class BatchTest {
    @Test
    fun `it defaults the batch number to 0`() {
        val batch = Batch()

        assertEquals(0, batch.number)
    }

    @Test
    fun `it defaults the recipe to null`() {
        val batch = Batch()

        assertNull(batch.recipe)
    }

    @Test
    fun `it defaults the flavor to null`() {
        val batch = Batch()

        assertEquals("", batch.flavor)
    }

    @Test
    fun `given a flavor, it should initialize its to what is given`() {
        val batch = Batch(flavor = "Coffee")

        assertEquals("Coffee", batch.flavor)
    }

    @Test
    fun `given a recipe, it should initialize its to what is given`() {
        val recipe = Recipe()
        val batch = Batch(recipe = recipe)

        assertEquals(recipe, batch.recipe)
    }

    // TODO: These dates really ought to be stubbed.
    @Test
    fun `it should default pour date to today`() {
        val batch = Batch()

        assertEquals(LocalDate.now(), batch.pourDate)
    }

    @Test
    fun `it should default cure date to six weeks after the pour date`() {
        val batch = Batch()

        assertEquals(LocalDate.now().plusWeeks(6), batch.cureDate)
    }

    @Nested
    inner class IsCured {
        @Test
        fun `when the cure date is tomorrow, it should return false`() {
            val batch = Batch(cureDate = LocalDate.now().plusDays(1))

            assertFalse(batch.isCured())
        }

        @Test
        fun `when the cure date is today, it should return true`() {
            val batch = Batch(cureDate = LocalDate.now())

            assertTrue(batch.isCured())
        }

        @Test
        fun `when the cure date is yesterday, it should return true`() {
            val batch = Batch(cureDate = LocalDate.now().minusDays(1))

            assertTrue(batch.isCured())
        }
    }

    @Nested
    inner class ToString {
        @Test
        fun `when the recipe is null, it just returns the batch number and recipe`() {
            val batch = Batch(number = 1738, flavor = "Lavender")

            assertEquals("Batch #1738: Lavender", batch.toString())
        }

        @Test
        fun `should format its name in a readable way`() {
            val recipe = Recipe(
                name = "Muppy Bar",
                version = "v2.0.2",
            )
            val batch = Batch(
                number = 42,
                recipe = recipe,
                flavor = "Coffee"
            )

            assertEquals("Batch #42: Coffee (Muppy Bar v2.0.2)", batch.toString())
        }
    }

    @Nested
    inner class calculateCost {
        // $1 per gram.
        private val rednersOliveOil = Product(
            netWeightAmount = 100.0f,
            netWeightUnit = GRAMS,
            priceInCents = 10000,
        )
        // $9 per gram.
        private val rednersAvocadoOil = Product(
            netWeightAmount = 100.0f,
            netWeightUnit = GRAMS,
            priceInCents = 90000,
        )
        // 10 grams of olive oil at $1/g = $10
        private val oliveOil = Ingredient(
            product = rednersOliveOil,
            measurementAmount = 10.0f,
            measurementUnit = GRAMS,
        )
        // 10 grams of olive oil at $10/g = $45
        private val avocadoOil = Ingredient(
            product = rednersAvocadoOil,
            measurementAmount = 50.0f,
            measurementUnit = GRAMS,
        )
        // 1.410958 oz of olive oil at $1/g = $40
        private val oliveOilInOunces = Ingredient(
            product = rednersOliveOil,
            measurementAmount = 1.410958f,
            measurementUnit = OUNCES,
        )
        // 6.667 mL of olive oil at $9/g = $60
        private val avocadoOilInMilliliters = Ingredient(
            product = rednersAvocadoOil,
            measurementAmount = 6.667f,
            measurementUnit = MILLILITERS,
        )

        @Test
        fun `when there are no ingredients in the batch, it should return 0`() {
            val batch = Batch(recipe = Recipe())

            assertEquals(0, batch.calculateCost())
        }

        @Test
        fun `given one ingredient, when the ingredient's cost per gram is $1 and the recipe calls for 10 grams, it should return 1000`() {
            val recipe = Recipe(ingredients = arrayListOf(oliveOil))
            val batch = Batch(recipe = recipe)

            assertEquals(1000, batch.calculateCost())
        }

        @Test
        fun `given both ingredients use grams, when the first ingredient costs $10 and the second ingredient costs $450, it should return 46000`() {
            val recipe = Recipe(ingredients = arrayListOf(oliveOil, avocadoOil))
            val batch = Batch(recipe = recipe)

            assertEquals(46000, batch.calculateCost())
        }

        @Test
        fun `given one ingredient uses ounces and the other uses milliliters, when their sum is $100, it should return 10000`() {
            val recipe = Recipe(ingredients = arrayListOf(oliveOilInOunces, avocadoOilInMilliliters))
            val batch = Batch(recipe = recipe)

            assertEquals(10000, batch.calculateCost())
        }
    }
}