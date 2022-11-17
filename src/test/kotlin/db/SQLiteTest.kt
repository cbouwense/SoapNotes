package db

import entities.Recipe
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.sql.DriverManager

internal class SQLiteTest {
    private val defaultDBName = "soapnotes"
    private val c = DriverManager.getConnection("jdbc:sqlite:${defaultDBName}.db")
    private val statementSpy = Mockito.spy(c.createStatement())

    @Test
    fun `create sends the right query to the database`() {
        val sqlite = SQLite(s = statementSpy)
        val recipe = Recipe(id = 42, name = "the answer", version = "4.2")

        sqlite.create(table = "recipes", recipe = recipe)

        Mockito.verify(statementSpy).executeUpdate("INSERT INTO recipes VALUES(42, 'the answer', '4.2')")
    }

    fun `it can create a recipe`() {
        val sqlite = SQLite(s = statementSpy)
        val recipe = Recipe(id = 42, name = "the answer", version = "4.2")

        sqlite.create(table = "recipes", recipe = recipe)
    }
}
