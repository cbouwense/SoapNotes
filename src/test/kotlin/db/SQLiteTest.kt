package db

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SQLiteTest {
    @Test
    fun `it can fetch a recipe by id`() {
        val sqlite = SQLite()
        val recipe = sqlite.getById(table = "recipes", id = 1337)

        assertEquals(1337, recipe.id)
        assertEquals("leet", recipe.name)
        assertEquals("13.37", recipe.version)
    }
}