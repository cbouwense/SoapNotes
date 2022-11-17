package db

import entities.Recipe
import use_cases.ports.Persistence
import java.sql.ResultSet
import java.sql.Statement

// Specific to Recipes, just to start out.
class SQLite(val s: Statement) : Persistence {
    override fun create(table: String, recipe: Recipe) {
        update("INSERT INTO ${table} VALUES(${recipe.id}, '${recipe.name}', '${recipe.version}')")
    }

    override fun getById(table: String, id: Int): Recipe {
        val result = query("SELECT * FROM ${table} WHERE id LIKE ${id}")
        println(result)

        return Recipe(
            id = id,
            name = result.getString("name"),
            version = result.getString("version")
        )
    }

    private fun update(sql: String): Int {
        return s.executeUpdate(sql)
    }

    private fun query(sql: String): ResultSet {
        return s.executeQuery(sql)
    }
}
