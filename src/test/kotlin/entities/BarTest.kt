package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BarTest {
    @Test
    fun `given no id, it should be initialized to 0`() {
        val bar = Bar()
        assertEquals(0, bar.id)
    }

    @Test
    fun `given an id, it should be initialized to that id`() {
        val bar = Bar(id = 42)
        assertEquals(42, bar.id)
    }
}