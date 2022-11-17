package db

import entities.Recipe
import use_cases.ports.Persistence
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

// Specific to Recipes, just to start out.
class SQLite : Persistence {
    override fun create(table: String, values: List<String>) {
        update("INSERT INTO ${table} VALUES(${values})")
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

    private val defaultDBName = "soapnotes"
    private val c = DriverManager.getConnection("jdbc:sqlite:${defaultDBName}.db")
    private val s = c.createStatement()

    private fun update(sql: String): Int {
        return s.executeUpdate(sql)
    }

    private fun query(sql: String): ResultSet {
        return s.executeQuery(sql)
    }

//                statement.executeUpdate("insert into person values(2, 'yui')")
//
//        val rs = statement.executeQuery("select * from person")
//        while(rs.next()) {
//            println("name = " + rs.getString("name"))
//            println("id = " + rs.getInt("id"))
//        }
}

// statement.executeUpdate("insert into person values(1, 'leo')")
