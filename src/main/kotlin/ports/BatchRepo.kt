package ports

import entities.Batch

interface BatchRepo {
    fun create(b: Batch): Int
    fun findById(id: Int): Batch?
    fun findByName(name: String): Batch?
    fun findByPourDate(pourDate: Int): Batch?
    fun getAll(): List<Batch>
}