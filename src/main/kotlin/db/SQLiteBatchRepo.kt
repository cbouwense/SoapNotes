package db

import entities.Batch
import ports.BatchRepo
import ports.RecipeRepo
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class SQLiteBatchRepo(val s: Statement, val recipeRepo: RecipeRepo) : BatchRepo {
    override fun create(b: Batch): Int {
        return update("INSERT INTO batches VALUES(${b.id}, '${b.name}')")
    }

    override fun getAll(): List<Batch> {
        val result = query("SELECT * FROM batches")

        val listOfBatches = ArrayList<Batch>()

        while (result.next()) {
            val recipe = recipeRepo.findById(result.getInt("recipe_id"))
            listOfBatches.add(
                Batch(
                    id = result.getInt("id"),
                    name = result.getString("name"),
                    pourDate = result.getInt("pour_date"),
                    cureDate = result.getInt("cure_date"),
                    recipe = recipe
                )
            )
        }

        return listOfBatches.toList()
    }

    override fun findById(id: Int): Batch? {
        val result = query("SELECT * FROM batches WHERE id = ${id}")
        val recipe = recipeRepo.findById(result.getInt("recipe_id"))

        return Batch(
            id = result.getInt("id"),
            name = result.getString("name"),
            pourDate = result.getInt("pour_date"),
            cureDate = result.getInt("cure_date"),
            recipe = recipe
        )
    }

    override fun findByName(name: String): Batch? {
        val result = query("SELECT * FROM batches WHERE name = ${name}")
        val recipe = recipeRepo.findById(result.getInt("recipe_id"))

        return Batch(
            id = result.getInt("id"),
            name = result.getString("name"),
            pourDate = result.getInt("pour_date"),
            cureDate = result.getInt("cure_date"),
            recipe = recipe
        )
    }

    override fun findByPourDate(pourDate: Int): Batch? {
        val result = query("SELECT * FROM batches WHERE pour_date = ${pourDate}")
        val recipe = recipeRepo.findById(result.getInt("recipe_id"))

        return Batch(
            id = result.getInt("id"),
            name = result.getString("name"),
            pourDate = result.getInt("pour_date"),
            cureDate = result.getInt("cure_date"),
            recipe = recipe
        )
    }

    // TODO: this should probably be in a parent class.
    private fun update(sql: String): Int {
        try {
            return s.executeUpdate(sql)
        } catch (e: SQLException) {
            return -1
        }
    }

    // TODO: this should probably be in a parent class.
    private fun query(sql: String): ResultSet {
            return s.executeQuery(sql)
        }
}
