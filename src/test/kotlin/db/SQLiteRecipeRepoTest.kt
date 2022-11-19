package db

import entities.Recipe
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.times
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SQLiteRecipeRepoTest {
    val resultSetFake = Mockito.mock(ResultSet::class.java)
    val statementSpy = Mockito.mock(Statement::class.java)
    val recipe = Recipe(id = 42, name = "the answer", version = "4.2")
    val sqlite = SQLiteRecipeRepo(s = statementSpy)

    @BeforeAll
    fun initializeMocks() {
        Mockito.`when`(resultSetFake.getInt("id")).thenReturn(42)
        Mockito.`when`(resultSetFake.getString("name")).thenReturn("the answer")
        Mockito.`when`(resultSetFake.getString("version")).thenReturn("4.2")
        Mockito.`when`(statementSpy.executeQuery(anyString())).thenReturn(resultSetFake)
    }

    @Test
    fun `sends the correct create update to sqlite`() {
        sqlite.create(recipe = recipe)

        Mockito.verify(statementSpy).executeUpdate("INSERT INTO recipes VALUES(42, 'the answer', '4.2')")
    }

    @Test
    fun `sends the correct query to find recipe by id`() {
        sqlite.findById(id = 42)

        Mockito.verify(statementSpy, times(1)).executeQuery("SELECT * FROM recipes WHERE id LIKE 42")
    }
}
