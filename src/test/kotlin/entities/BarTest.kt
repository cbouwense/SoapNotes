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

    @Test
    fun `given no Flavor, it should be initialized to UNSCENTED`() {
        val bar = Bar()
        assertEquals(Flavor.UNSCENTED, bar.flavor)
    }

    @Test
    fun `given a Flavor, it should be initialized to that Flavor`() {
        val bar = Bar(flavor = Flavor.COFFEE)
        assertEquals(Flavor.COFFEE, bar.flavor)
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
}