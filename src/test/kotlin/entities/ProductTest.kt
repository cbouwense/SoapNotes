package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductTest {
    @Test
    fun `it should default its name to an empty string`() {
        val product = Product()

        assertEquals("", product.name)
    }

    @Test
    fun `given a name, it should be initialized to that name`() {
        val product = Product(name = "Redner's Olive Oil")

        assertEquals("Redner's Olive Oil", product.name)
    }

    @Test
    fun `it can change its name`() {
        val product = Product(name = "Redner's Olive Oil")

        product.name = "Wegner's Olive Oil"

        assertEquals("Wegner's Olive Oil", product.name)
    }

    @Test
    fun `it should default net weight amount to 0`() {
        val product = Product()

        assertEquals(0.0f, product.netWeightAmount)
    }

    @Test
    fun `given a net weight amount, it should be initialized to that amount`() {
        val product = Product(netWeightAmount = 4.2f)

        assertEquals(4.2f, product.netWeightAmount)
    }

    @Test
    fun `it should default net weight units to ounces`() {
        val product = Product()

        assertEquals(MeasurementUnit.OUNCES, product.netWeightUnit)
    }

    @Test
    fun `given a net weight unit, it should be initialized to that unit`() {
        val product = Product(netWeightUnit = MeasurementUnit.GRAMS)

        assertEquals(MeasurementUnit.GRAMS, product.netWeightUnit)
    }

    @Test
    fun `it should default price to 0`() {
        val product = Product()

        assertEquals(0, product.priceInCents)
    }

    @Test
    fun `given a price, it should be initialized to that price`() {
        val product = Product(priceInCents = 1337)

        assertEquals(1337, product.priceInCents)
    }

    @Nested
    inner class GetPricePerGram {
        @Test
        fun `when the product measurement unit is grams, it should return the price in cents divided by the measurement amount`() {
            val product = Product(
                priceInCents = 42,
                netWeightUnit = MeasurementUnit.GRAMS,
                netWeightAmount = 1.0f,
            )

            assertEquals(42, product.getCentsPerGram())
        }

        @Test
        fun `when the product measurement amount is 0, it should throw an ArithmeticException`() {
            val product = Product(priceInCents = 42)

            assertThrows<ArithmeticException>{ product.getCentsPerGram() }
        }

        @Test
        fun `when the product measurement unit is not grams, it should convert to grams and return the price in cents divided by the measurement amount`() {
            val product = Product(
                priceInCents = 1337,
                netWeightUnit = MeasurementUnit.OUNCES,
                netWeightAmount = 4.2f,
            )

            assertEquals(11, product.getCentsPerGram())
        }
    }

}