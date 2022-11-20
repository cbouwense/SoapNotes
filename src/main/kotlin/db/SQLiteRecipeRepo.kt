package db

import entities.Recipe
import ports.RecipeRepo
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class SQLiteRecipeRepo(val s: Statement) : RecipeRepo {
    override fun create(recipe: Recipe): Int {
        return update("INSERT INTO recipes VALUES(${recipe.id}, '${recipe.name}', '${recipe.version}')")
    }

    override fun findById(id: Int): Recipe {
        val result = query("SELECT * FROM recipes WHERE id = ${id}")

        return Recipe(
            id = result.getInt("id"),
            name = result.getString("name"),
            version = result.getString("version")
        )
    }

    override fun findByNameAndVersion(name: String, version: String): Recipe? {
        val result = query("SELECT * FROM recipes WHERE name = ${name} AND version = ${version}")

        return Recipe(
            id = result.getInt("id"),
            name = result.getString("name"),
            version = result.getString("version")
        )
    }

    override fun getAll(): List<Recipe> {
        val result = query("SELECT * FROM recipes")

        val listOfRecipes = ArrayList<Recipe>()

        while (result.next()) {
            listOfRecipes.add(
                Recipe(
                    id = result.getInt("id"),
                    name = result.getString("name"),
                    version = result.getString("version")
                )
            )
        }

        return listOfRecipes.toList()
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
