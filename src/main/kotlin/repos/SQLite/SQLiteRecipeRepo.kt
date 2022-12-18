package repos.SQLite

import entities.Recipe
import org.sqlite.SQLiteException
import ports.RecipeRepo
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class SQLiteRecipeRepo(override val s: Statement) : SQLiteRepo(s), RecipeRepo {
    override fun create(recipe: Recipe): Int {
        return update("INSERT INTO recipes VALUES(NULL, '${recipe.name}', '${recipe.version}')")
    }

    override fun findById(id: Int): Recipe? {
        val result = query("SELECT * FROM recipes WHERE id = ${id}")
        if (result == null) return null

        return translateResultSetToEntity(result)
    }

    override fun findByNameAndVersion(name: String, version: String): Recipe? {
        val result = query("SELECT * FROM recipes WHERE name = ${name} AND version = ${version}")
        if (result == null) return null

        return translateResultSetToEntity(result)
    }

    override fun getAll(): List<Recipe> {
        val result = query("SELECT * FROM recipes")
        val listOfRecipes = ArrayList<Recipe>()
        if (result == null) return listOfRecipes.toList()

        while (result.next()) {
            val recipe = translateResultSetToEntity(result)
            if (recipe == null) break
            listOfRecipes.add(recipe)
        }

        return listOfRecipes.toList()
    }

    override fun getMaxId(): Int {
        val result = query("SELECT MAX(id) FROM recipes")
        if (result == null) return -1

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
}
