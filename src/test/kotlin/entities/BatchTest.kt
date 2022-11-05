package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

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

    @Nested
    class ToString {
        @Test fun `when the recipe is null, it just returns the batch number and recipe`() {
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
}