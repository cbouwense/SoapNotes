package repos

import entities.Recipe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.times
import repos.SQLite.SQLiteRecipeRepo
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

internal class SQLiteRecipeRepoTest {
    val resultSetFake = Mockito.mock(ResultSet::class.java)
    val statementSpy = Mockito.mock(Statement::class.java)

    val id = 42
    val name = "the answer"
    val version = "4.2"
    val recipe = Recipe(id = id, name = name, version = version)
    val sqlite = SQLiteRecipeRepo(s = statementSpy)

    @BeforeEach
    fun beforeEach() {
        Mockito.`when`(resultSetFake.getInt("id")).thenReturn(id)
        Mockito.`when`(resultSetFake.getString("name")).thenReturn(name)
        Mockito.`when`(resultSetFake.getString("version")).thenReturn(version)
        Mockito.`when`(statementSpy.executeQuery(anyString())).thenReturn(resultSetFake)
    }

    @Nested
    inner class Create {
        @BeforeEach
        fun beforeEach() {
            Mockito.`when`(statementSpy.executeUpdate(anyString())).thenReturn(1)
        }

        @Test
        fun `sends the correct create update to sqlite`() {
            sqlite.create(recipe = recipe)

            Mockito.verify(statementSpy, times(1)).executeUpdate("INSERT INTO recipes VALUES($id, '$name', '$version')")
        }

        @Test
        fun `when the creation is a success, returns the result from the update`() {
            assertEquals(1, sqlite.create(recipe = recipe))
        }

        @Test
        fun `when the creation is a failure, returns a non-zero code`() {
            Mockito.`when`(statementSpy.executeUpdate(anyString())).thenThrow(SQLException::class.java)

            assertEquals(-1, sqlite.create(recipe = recipe))
        }
    }

    @Nested
    inner class FindById {
        @Test
        fun `sends the correct query to find recipe by id`() {
            sqlite.findById(id = id)

            Mockito.verify(statementSpy, times(1)).executeQuery("SELECT * FROM recipes WHERE id = $id")
        }

        @Test
        fun `when there is a corresponding recipe in the db, returns the Recipe`() {
            val result = sqlite.findById(id = id)

            assertThat(result).usingRecursiveComparison().isEqualTo(recipe)
        }
    }

    @Nested
    inner class FindByNameAndVersion {
        @Test
        fun `send the correct query to find recipe by name and version`() {
            sqlite.findByNameAndVersion(name = name, version = version)

            Mockito.verify(statementSpy, times(1))
                .executeQuery("SELECT * FROM recipes WHERE name = $name AND version = $version")
        }

        @Test
        fun `when there is a recipe with the given name and version in the db, returns the Recipe`() {
            val result = sqlite.findByNameAndVersion(name = name, version = version)

            assertThat(result).usingRecursiveComparison().isEqualTo(recipe)
        }
    }

    @Nested
    inner class GetAll {
        val recipe1 = Recipe(id = id, name = "${name}0", version = "${version}.0")
        val recipe2 = Recipe(id = id + 1, name = "${name}1", version = "${version}.1")
        val recipe3 = Recipe(id = id + 2, name = "${name}2", version = "${version}.2")

        @BeforeEach
        fun beforeEach() {
            Mockito.`when`(resultSetFake.getInt("id"))
                .thenReturn(recipe1.id)
                .thenReturn(recipe2.id)
                .thenReturn(recipe3.id)
            Mockito.`when`(resultSetFake.getString("name"))
                .thenReturn(recipe1.name)
                .thenReturn(recipe2.name)
                .thenReturn(recipe3.name)
            Mockito.`when`(resultSetFake.getString("version"))
                .thenReturn(recipe1.version)
                .thenReturn(recipe2.version)
                .thenReturn(recipe3.version)
            Mockito.`when`(resultSetFake.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false)
            Mockito.`when`(statementSpy.executeQuery(anyString())).thenReturn(resultSetFake)
        }

        @Test
        fun `sends the correct query to get all recipes`() {
            sqlite.getAll()

            Mockito.verify(statementSpy, times(1)).executeQuery("SELECT * FROM recipes")
        }

        @Test
        fun `when there are recipe in the db, returns a list of Recipes`() {
            val result = sqlite.getAll()

            assertThat(result).usingRecursiveComparison().isEqualTo(listOf(recipe1, recipe2, recipe3))
        }
    }

    @Nested
    inner class GetMaxId {
        @Test
        fun `send the correct query to find latest recipe id`() {
            sqlite.getMaxId()

            Mockito.verify(statementSpy, times(1)).executeQuery("SELECT MAX(id) FROM recipes")
        }

        @Test
        fun `when there is a recipe with the given name and version in the db, returns the Recipe`() {
            assertEquals(42, sqlite.getMaxId())
        }
    }

}
