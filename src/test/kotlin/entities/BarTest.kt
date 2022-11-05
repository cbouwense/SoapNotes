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
    fun `it defaults its batch to null`() {
        val bar = Bar()

        assertNull(bar.batch)
    }

    @Test
    fun `given a batch, it should be initialized to that recipe`() {
        val batch = Batch()
        val bar = Bar(batch = batch)

        assertEquals(batch, bar.batch)
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