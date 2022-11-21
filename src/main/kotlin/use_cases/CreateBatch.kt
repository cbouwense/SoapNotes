package use_cases

import entities.Batch
import ports.BatchRepo

class CreateBatch(val batch: Batch, val batchRepo: BatchRepo) {
    fun run(): Int {
        // TODO: Maybe I could initialize the pour and cure dates here.
        return batchRepo.create(b = batch)
    }
}