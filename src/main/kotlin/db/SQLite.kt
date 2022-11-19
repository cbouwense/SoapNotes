package db

import entities.Recipe
import ports.RecipeRepo
import use_cases.ports.Persistence
import java.sql.ResultSet
import java.sql.Statement

class SQLiteRecipeRepo(val s: Statement) : RecipeRepo {
    override fun create(recipe: Recipe): Int {
        return update("INSERT INTO recipes VALUES(${recipe.id}, '${recipe.name}', '${recipe.version}')")
    }

    override fun findById(id: Int): Recipe {
        val result = query("SELECT * FROM recipes WHERE id LIKE ${id}")

        return Recipe(
            id = result.getInt("id"),
            name = result.getString("name"),
            version = result.getString("version")
        )
    }

    override fun findByNameAndVersion(name: String, version: String): Recipe? {
        val result = query("SELECT * FROM recipes WHERE name LIKE ${name} AND version LIKE ${version}")

        return Recipe(
            id = result.getInt("id"),
            name = result.getString("name"),
            version = result.getString("version")
        )
    }

    // TODO: this should probably be in a parent class.
    private fun update(sql: String): Int {
        return s.executeUpdate(sql)
    }

    // TODO: this should probably be in a parent class.
    private fun query(sql: String): ResultSet {
        return s.executeQuery(sql)
    }
}
