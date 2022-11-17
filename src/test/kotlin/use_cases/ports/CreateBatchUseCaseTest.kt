package use_cases.ports

import org.junit.jupiter.api.Test

internal class CreateBatchUseCaseTest {
    @Test
    fun `it can create a table`() {
        val batchCreator = CreateBatchUseCase()
        batchCreator.readFromDB()
    }

    @Test
    fun `it can read a CSV file`() {
        val batchCreator = CreateBatchUseCase()

    }
}