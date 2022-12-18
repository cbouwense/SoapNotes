package repos

import entities.Recipe
import org.sqlite.SQLiteException
import ports.RecipeRepo
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class SQLiteRecipeRepo(val s: Statement) : RecipeRepo {
    override fun create(recipe: Recipe): Int {
        return update("INSERT INTO recipes VALUES(NULL, '${recipe.name}', '${recipe.version}')")
    }

    override fun findById(id: Int): Recipe? {
        val result = query("SELECT * FROM recipes WHERE id = ${id}")
        return translateResultSetToEntity(result)
    }

    override fun findByNameAndVersion(name: String, version: String): Recipe? {
        val result = query("SELECT * FROM recipes WHERE name = ${name} AND version = ${version}")
        return translateResultSetToEntity(result)
    }

    override fun getAll(): List<Recipe> {
        val result = query("SELECT * FROM recipes")
        val listOfRecipes = ArrayList<Recipe>()

        while (result.next()) {
            val recipe = translateResultSetToEntity(result)
            if (recipe == null) break
            listOfRecipes.add(recipe)
        }

        return listOfRecipes.toList()
    }

    override fun getMaxId(): Int {
        val result = query("SELECT MAX(id) FROM recipes")
        return result.getInt("MAX(id)")
    }

    fun translateResultSetToEntity(r: ResultSet): Recipe? {
        val recipeId = try { r.getInt("id") } catch (e: SQLiteException) { null }
        val recipeName = try { r.getString("name") } catch (e: SQLiteException) { null }
        val recipeVersion = try { r.getString("version") } catch (e: SQLiteException) { null }

        if (recipeId == null || recipeName == null || recipeVersion == null) return null

        return Recipe(
            id = recipeId,
            name = recipeName,
            version = recipeVersion
        )
    }

    // TODO: this should probably be in a parent class.
    private fun update(sql: String): Int {
        try {
            return s.executeUpdate(sql)
        } catch (e: SQLException) {
            println(e.toString())
            return -1
        }
    }

    // TODO: this should probably be in a parent class.
    private fun query(sql: String): ResultSet {
        return s.executeQuery(sql)
    }
}
