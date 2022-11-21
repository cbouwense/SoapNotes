package use_cases

import entities.Batch
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import ports.BatchRepo

internal class CreateBatchTest {
    val batchRepoSpy = mock(BatchRepo::class.java)

    @Test
    fun `invoking CreateBatch calls the create method on the batch repo with the batch`() {
        val batch = Batch()

        CreateBatch(batch = batch, batchRepo = batchRepoSpy).run()

        verify(batchRepoSpy, times(1)).create(batch)
    }
}
