package repos.SQLite

import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

open class SQLiteRepo(open val s: Statement) {
    fun update(sql: String): Int {
        try {
            return s.executeUpdate(sql)
        } catch (e: SQLException) {
            println("Error executing update: ${e.message}")
            return -1
        }
    }

    fun query(sql: String): ResultSet? {
        try {
            return s.executeQuery(sql)
        } catch (e: SQLException) {
            println("Error executing query: ${e.message}")
            return null
        }
    }
}