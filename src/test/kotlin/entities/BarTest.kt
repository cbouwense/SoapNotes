package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BarTest {
    @Test
    fun `constructing a Bar should not throw an exception`() {
        val bar = Bar(1)
    }
}