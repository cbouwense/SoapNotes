import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested


class MainKtTest {
    @Nested
    inner class AddIntsTest {
        @Test
        fun `given2And2_shouldReturn4`() {
            assertEquals(4, addInts(2, 2))
        }

        @Test
        fun `given0And0_shouldReturn0`() {
            assertEquals(0, addInts(0, 0))
        }
    }

    @Nested
    inner class AddIntsTestTwo {
        @Test
        fun `given1And1_shouldReturn2`() {
            assertEquals(2, addInts(1, 1))
        }

        @Test
        fun `given-1And1_shouldReturn0`() {
            assertEquals(0, addInts(-1, 1))
        }
    }
}