package use_cases

import entities.Batch
import ports.BatchRepo

class GetAllBatches(val batchRepo: BatchRepo) {
    fun run(): List<Batch> {
        return batchRepo.getAll()
    }
}