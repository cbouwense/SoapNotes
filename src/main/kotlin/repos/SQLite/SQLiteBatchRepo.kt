package repos.SQLite

import entities.Batch
import ports.BatchRepo
import ports.RecipeRepo
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class SQLiteBatchRepo(override val s: Statement, val recipeRepo: RecipeRepo) : SQLiteRepo(s), BatchRepo {
    override fun create(b: Batch): Int {
        return update("INSERT INTO batches VALUES (NULL, ${b.pourDate}, ${b.cureDate}, '${b.name}', ${b.recipe?.id})")
    }

    override fun getAll(): List<Batch> {
        val result = query("SELECT * FROM batches")
        val listOfBatches = ArrayList<Batch>()

        if (result == null) return listOfBatches.toList()

        while (result.next()) {
            val batch = translateResultSetToEntity(result)
            if (batch == null) break
            listOfBatches.add(batch)
        }

        return listOfBatches.toList()
    }

    override fun findById(id: Int): Batch? {
        val result = query("SELECT * FROM batches WHERE id = ${id}")
        if (result == null) return null

        return translateResultSetToEntity(result)
    }

    override fun findByName(name: String): Batch? {
        val result = query("SELECT * FROM batches WHERE name = ${name}")
        if (result == null) return null

        return translateResultSetToEntity(result)
    }

    override fun findByPourDate(pourDate: Int): Batch? {
        val result = query("SELECT * FROM batches WHERE pour_date = ${pourDate}")
        if (result == null) return null

        return translateResultSetToEntity(result)
    }

    fun translateResultSetToEntity(r: ResultSet): Batch? {
        val batchId = r.getInt("id")
        val batchName = r.getString("name")
        val pourDate = r.getLong("pour_date")
        val cureDate = r.getLong("cure_date")
        val recipe = recipeRepo.findById(r.getInt("recipe_id"))

        if (batchId == null || batchName == null || pourDate == null || cureDate == null || recipe == null) return null

        return Batch(
            id = batchId,
            name = batchName,
            pourDate = pourDate,
            cureDate = cureDate,
            recipe = recipe
        )
    }
}
