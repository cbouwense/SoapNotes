package use_cases

import entities.Batch
import ports.BatchRepo

class CreateBatch(val batch: Batch, val batchRepo: BatchRepo) {
    fun run(): Int {
        return batchRepo.create(b = batch)
    }
}