package db

import entities.Recipe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

// TODO: this should probably be fetching from a spy database
internal class SQLiteTest {
    @Test
    fun `it can fetch a recipe by id`() {
        val sqlite = SQLite()
        val recipe = sqlite.getById(table = "recipes", id = 1337)

        assertEquals(1337, recipe.id)
        assertEquals("leet", recipe.name)
        assertEquals("13.37", recipe.version)
    }

    @Test
    fun `it can create a recipe`() {
        val sqlite = SQLite()
        val recipe = Recipe(id = 42, name = "the answer", version = "4.2")

        sqlite.create(table = "recipes", recipe = recipe)
    }
}