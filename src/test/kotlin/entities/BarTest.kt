package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class BarTest {
    val recipe = Recipe()

    @Test
    fun `given an id, it should be initialized to that id`() {
        val uuid = UUID.randomUUID()
        val bar = Bar(id = uuid, recipe = recipe)

        assertEquals(uuid, bar.id)
    }

    @Test
    fun `given no flavor, it should be initialized to null`() {
        val bar = Bar(recipe = recipe)

        assertNull(bar.recipe)
    }

    @Test
    fun `given a recipe, it should be initialized to that recipe`() {
        val recipe = Recipe(name = "coffee")
        val bar = Bar(recipe = recipe)

        assertEquals(recipe, bar.recipe)
    }

    @Test
    fun `given no isCured, it should be initialized to false`() {
        val bar = Bar(recipe = recipe)

        assertFalse(bar.isCured)
    }

    @Test
    fun `given an isCured, it should be initialized to that isCured`() {
        val bar = Bar(isCured = true, recipe = recipe)

        assertTrue(bar.isCured)
    }

    @Test
    fun `it should be able to change isCured`() {
        val bar = Bar(recipe = recipe)

        bar.isCured = true

        assertTrue(bar.isCured)
    }

    @Test
    fun `given no owner, it should be initialized to null`() {
        val bar = Bar(recipe = recipe)

        assertNull(bar.owner)
    }

    @Test
    fun `given an owner, it should be initialized to that owner`() {
        val scrumpy = Person(name = "Scrumpy")
        val bar = Bar(owner = scrumpy, recipe = recipe)

        assertEquals(bar.owner, scrumpy)
    }

    @Test
    fun `it should be able to change a bar's owner`() {
        val scrumpy = Person(name = "Scrumpy")
        val rachel = Person(name = "Rachel")
        val bar = Bar(owner = scrumpy, recipe = recipe)

        bar.owner = rachel

        assertEquals(bar.owner, rachel)
    }
}