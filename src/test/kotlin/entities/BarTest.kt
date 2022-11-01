package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BarTest {
    @Test
    fun `constructing a Bar should not throw an exception`() {
        Bar()
    }

    @Test
    fun `given an id, it should be initialized with that id`() {
        val bar = Bar(id = 42)
        assertEquals(42, bar.id)
    }
}