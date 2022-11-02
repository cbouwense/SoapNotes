package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class RecipeTest {
    @Test
    fun `given an id, it should be initialized to that id`() {
        val uuid = UUID.randomUUID()
        println(uuid)
        val recipe = Recipe(id = uuid)

        assertEquals(uuid, recipe.id)
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

    @Test
    fun `given a version, it should be initialized to that version`() {
        val recipe = Recipe(version = "v1.0.0")

        assertEquals("v1.0.0", recipe.version)
    }

    @Test
    fun `should be able to change the version`() {
        val recipe = Recipe(version = "v1.0.0")

        recipe.version = "v1.0.1"

        assertEquals("v1.0.1", recipe.version)
    }
}