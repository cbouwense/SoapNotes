package db

import entities.Recipe
import org.junit.jupiter.api.Assertions.assertEquals
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

    val id = 42
    val name = "the answer"
    val version = "4.2"
    val recipe = Recipe(id = id, name = name, version = version)
    val sqlite = SQLiteRecipeRepo(s = statementSpy)

    @BeforeEach
    fun initializeMocks() {
        Mockito.`when`(resultSetFake.getInt("id")).thenReturn(id)
        Mockito.`when`(resultSetFake.getString("name")).thenReturn(name)
        Mockito.`when`(resultSetFake.getString("version")).thenReturn(version)
        Mockito.`when`(statementSpy.executeQuery(anyString())).thenReturn(resultSetFake)
    }

    @Test
    fun `sends the correct create update to sqlite`() {
        sqlite.create(recipe = recipe)

        Mockito.verify(statementSpy, times(1)).executeUpdate("INSERT INTO recipes VALUES($id, '$name', '$version')")
    }

    @Test
    fun `when the creation is a success, returns 0`() {
        assertEquals(0, sqlite.create(recipe = recipe))
    }

    @Test
    fun `sends the correct query to find recipe by id`() {
        sqlite.findById(id = id)

        Mockito.verify(statementSpy, times(1)).executeQuery("SELECT * FROM recipes WHERE id LIKE $id")
    }

    @Test
    fun `when there is a corresponding recipe in the db, returns the Recipe`() {
        val result = sqlite.findById(id = id)

        // TODO: figure out how to do deep equality in tests.
        assertEquals(recipe.id, result.id)
        assertEquals(recipe.name, result.name)
        assertEquals(recipe.version, result.version)
    }

    @Test
    fun `send the correct query to find recipe by name and version`() {
        sqlite.findByNameAndVersion(name = name, version = version)

        Mockito.verify(statementSpy, times(1)).executeQuery("SELECT * FROM recipes WHERE name LIKE $name AND version LIKE $version")
    }

    @Test
    fun `when there is a recipe with the given name and version in the db, returns the Recipe`() {
        val result = sqlite.findByNameAndVersion(name = name, version = version)

        // TODO: figure out how to do deep equality in tests.
        assertEquals(recipe.id, result?.id)
        assertEquals(recipe.name, result?.name)
        assertEquals(recipe.version, result?.version)
    }
}
