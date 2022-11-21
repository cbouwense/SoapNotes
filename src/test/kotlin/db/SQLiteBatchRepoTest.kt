package db

import entities.Batch
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import ports.RecipeRepo
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement


internal class SQLiteBatchRepoTest {
    val resultSetFake = mock(ResultSet::class.java)
    val statementSpy = mock(Statement::class.java)
    val recipeRepoFake = mock(RecipeRepo::class.java)

    val id = 42
    val name = "the answer"
    val pourDate = 1337
    val cureDate = 1738
    val batch = Batch(id = id, name = name, pourDate = pourDate, cureDate = cureDate)

    val sqlite = SQLiteBatchRepo(s = statementSpy, recipeRepo = recipeRepoFake)

    @BeforeEach
    fun beforeEach() {
        `when`(resultSetFake.getInt("id")).thenReturn(id)
        `when`(resultSetFake.getString("name")).thenReturn(name)
        `when`(resultSetFake.getInt("pour_date")).thenReturn(pourDate)
        `when`(resultSetFake.getInt("cure_date")).thenReturn(cureDate)
        `when`(statementSpy.executeQuery(anyString())).thenReturn(resultSetFake)
    }

    @Nested
    inner class Create {
        @BeforeEach
        fun beforeEach() {
            Mockito.`when`(statementSpy.executeUpdate(anyString())).thenReturn(1)
        }

        @Test
        fun `sends the correct create update to sqlite`() {
            sqlite.create(batch)

            Mockito.verify(statementSpy, times(1)).executeUpdate("INSERT INTO batches VALUES($id, '$name')")
        }

        @Test
        fun `when the creation is a success, returns the result from the update`() {
            assertEquals(1, sqlite.create(batch))
        }

        @Test
        fun `when the creation is a failure, returns a non-zero code`() {
            Mockito.`when`(statementSpy.executeUpdate(anyString())).thenThrow(SQLException::class.java)

            assertEquals(-1, sqlite.create(batch))
        }
    }

    @Nested
    inner class FindById {
        @Test
        fun `sends the correct query to find batch by id`() {
            sqlite.findById(id)

            verify(statementSpy, times(1)).executeQuery("SELECT * FROM batches WHERE id = $id")
        }

        @Test
        fun `when there is a corresponding batch in the db, returns the Batch`() {
            val result = sqlite.findById(id)

            assertThat(result).usingRecursiveComparison().isEqualTo(batch)
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
}
