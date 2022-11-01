package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RecipeTest {
    @Test
    fun `given no id, it should be initialized to 0`() {
        val recipe = Recipe()

        assertEquals(0, recipe.id)
    }

    @Test
    fun `given an id, it should be initialized to that id`() {
        val recipe = Recipe(id = 42)

        assertEquals(42, recipe.id)
    }

    @Test
    fun `given a name, it should be initialized to that name`() {
        val recipe = Recipe(name = "coffee")

        assertEquals("coffee", recipe.name)
    }

    @Test
    fun `should be able to change a recipe's name`() {
        val recipe = Recipe(name = "coffee")

        recipe.name = "lemon poppyseed"

        assertEquals("lemon poppyseed", recipe.name)
    }
}