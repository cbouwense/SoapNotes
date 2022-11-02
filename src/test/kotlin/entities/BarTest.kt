package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class BarTest {
    @Test
    fun `given an id, it should be initialized to that id`() {
        val uuid = UUID.randomUUID()
        val bar = Bar(id = uuid)

        assertEquals(uuid, bar.id)
    }

    @Test
    fun `given no flavor, it should be initialized to null`() {
        val bar = Bar()

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
        val bar = Bar()

        assertFalse(bar.isCured)
    }

    @Test
    fun `given an isCured, it should be initialized to that isCured`() {
        val bar = Bar(isCured = true)

        assertTrue(bar.isCured)
    }

    @Test
    fun `it should be able to change isCured`() {
        val bar = Bar()

        bar.isCured = true

        assertTrue(bar.isCured)
    }

    @Test
    fun `given no owner, it should be initialized to null`() {
        val bar = Bar()

        assertNull(bar.owner)
    }

    @Test
    fun `given an owner, it should be initialized to that owner`() {
        val scrumpy = Person(name = "Scrumpy")
        val bar = Bar(owner = scrumpy)

        assertEquals(bar.owner, scrumpy)
    }

    @Test
    fun `it should be able to change a bar's owner`() {
        val scrumpy = Person(name = "Scrumpy")
        val rachel = Person(name = "Rachel")
        val bar = Bar(owner = scrumpy)

        bar.owner = rachel

        assertEquals(bar.owner, rachel)
    }
}