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
        @Test
        fun `when there are no ingredients in the batch, it should return 0`() {
            val batch = Batch(recipe = Recipe())

            assertEquals(0, batch.calculateCost())
        }

//        @Test
//        fun `when the ingredients' costs sum up to $100, it should return 10000`() {
//            // $1 per gram.
//            val rednersOliveOil = Product(
//                netWeightAmount = 100.0f,
//                netWeightUnit = GRAMS,
//                priceInCents = 10000,
//            )
//            // 10 grams of olive oil at $1/g = $10
//            val oliveOil = Ingredient(
//                product = rednersOliveOil,
//                measurementAmount = 10.0f,
//                measurementUnit = GRAMS,
//            )
//            val recipe = Recipe(ingredients = arrayListOf(oliveOil))
//            val batch = Batch(recipe = recipe)
//
//            assertEquals(10000, batch.calculateCost())
//        }
    }
}