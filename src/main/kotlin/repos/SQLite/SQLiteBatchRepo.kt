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
            if (batch == null) continue

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
        val batchId = try { r.getInt("id") } catch (e: SQLException) { null }
        val batchPourDate = try { r.getInt("pour_date").toLong() } catch (e: SQLException) { null }
        val batchCureDate = try { r.getInt("cure_date").toLong() } catch (e: SQLException) { null }
        val batchName = try { r.getString("name") } catch (e: SQLException) { null }
        val batchRecipeId = try { r.getInt("recipe_id") } catch (e: SQLException) { null }
        val recipe = if (batchRecipeId != null) { recipeRepo.findById(batchRecipeId) } else null

        if (batchId == null || batchName == null || batchPourDate == null || batchCureDate == null) return null

        return Batch(
            id = batchId,
            name = batchName,
            pourDate = batchPourDate,
            cureDate = batchCureDate,
            recipe = recipe
        )
    }
}
