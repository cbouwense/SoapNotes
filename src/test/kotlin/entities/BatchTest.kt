package entities

import entities.MeasurementUnit.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset

internal class BatchTest {
    @Test
    fun `it defaults the batch id to 0`() {
        val batch = Batch()

        assertEquals(0, batch.id)
    }

    @Test
    fun `it defaults the recipe to null`() {
        val batch = Batch()

        assertNull(batch.recipe)
    }

    @Test
    fun `it defaults the name to null`() {
        val batch = Batch()

        assertEquals("", batch.name)
    }

    @Test
    fun `given a name, it should initialize its to what is given`() {
        val batch = Batch(name = "Coffee")

        assertEquals("Coffee", batch.name)
    }

    @Test
    fun `given a recipe, it should initialize its to what is given`() {
        val recipe = Recipe()
        val batch = Batch(recipe = recipe)

        assertEquals(recipe, batch.recipe)
    }

    @Test
    fun `when there are no bars in the batch, it should return 0`() {
        val batch = Batch()

        assertEquals(0, batch.bars.size)
    }

    @Test
    fun `when there is 1 bar in the batch, it should return 1`() {
        val batch = Batch(bars = arrayListOf(Bar()))

        assertEquals(1, batch.bars.size)
    }

    @Test
    fun `when there are 3 bars in the batch, it should return 3`() {
        val batch = Batch(bars = arrayListOf(Bar(), Bar(), Bar()))

        assertEquals(3, batch.bars.size)
    }

    @Nested
    inner class CutIntoBars {
        @Test
        fun `given 0 bars to cut, it should not increase the id of bars in the batch`() {
            val batch = Batch()

            batch.cutIntoBars(0)

            assertEquals(0, batch.bars.size)
        }

        @Test
        fun `given 1 bar to cut, it should increase the id of bars to 1`() {
            val batch = Batch()

            batch.cutIntoBars(1)

            assertEquals(1, batch.bars.size)
        }

        @Test
        fun `given 1 bar to cut, it should add a bar with the recipe to its list`() {
            val batch1 = Batch(id = 1)

            batch1.cutIntoBars(1)

            assertEquals(batch1, batch1.bars.first().batch)
        }

        @Test
        fun `given 12 bars to cut, it should increase the id of bars to 12`() {
            val batch = Batch(id = 42)

            batch.cutIntoBars(12)

            assertTrue(batch.bars.all { it.batch == batch })
        }
    }

    @Nested
    inner class IsCured {
        // TODO: gotta figure out this date shit
//        @Test
//        fun `when the cure date is tomorrow, it should return false`() {
//            val batch = Batch(cureDate = )
//
//            assertFalse(batch.isCured())
//        }

        @Test
        fun `when the cure date is today, it should return true`() {
            val batch = Batch(cureDate = LocalDate.now().toEpochSecond(LocalTime.now(), ZoneOffset.UTC).toInt())

            assertTrue(batch.isCured())
        }

        @Test
        fun `when the cure date is yesterday, it should return true`() {
            val batch = Batch(cureDate = LocalDate.now().minusDays(1).toEpochSecond(LocalTime.now(), ZoneOffset.UTC).toInt())

            assertTrue(batch.isCured())
        }
    }

    @Nested
    inner class ToString {
        @Test
        fun `when the recipe is null, it just returns the batch id and recipe`() {
            val batch = Batch(id = 1738, name = "Lavender")

            assertEquals("Batch #1738: Lavender", batch.toString())
        }

        @Test
        fun `should format its name in a readable way`() {
            val recipe = Recipe(
                name = "Muppy Bar",
                version = "v2.0.2",
            )
            val batch = Batch(
                id = 42,
                recipe = recipe,
                name = "Coffee"
            )

            assertEquals("Batch #42: Coffee (Muppy Bar v2.0.2)", batch.toString())
        }
    }

    @Nested
    inner class CalculateCost {
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
        fun `when the batch has no recipe, it should return 0`() {
            val batch = Batch()

            assertEquals(0, batch.calculateCost())
        }

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