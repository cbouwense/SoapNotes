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
    inner class GetAll {
        val batch1 = Batch(id = id, name = "${name}1", pourDate = id + 1, cureDate = id + 11)
        val batch2 = Batch(id = id + 1, name = "${name}2", pourDate = id + 2, cureDate = id + 12)
        val batch3 = Batch(id = id + 2, name = "${name}3", pourDate = id + 3, cureDate = id + 13)

        @BeforeEach
        fun beforeEach() {
            `when`(resultSetFake.getInt("id"))
                .thenReturn(batch1.id)
                .thenReturn(batch2.id)
                .thenReturn(batch3.id)
            `when`(resultSetFake.getString("name"))
                .thenReturn(batch1.name)
                .thenReturn(batch2.name)
                .thenReturn(batch3.name)
            `when`(resultSetFake.getInt("pour_date"))
                .thenReturn(batch1.pourDate)
                .thenReturn(batch2.pourDate)
                .thenReturn(batch3.pourDate)
            `when`(resultSetFake.getInt("cure_date"))
                .thenReturn(batch1.cureDate)
                .thenReturn(batch2.cureDate)
                .thenReturn(batch3.cureDate)
            `when`(resultSetFake.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false)
            `when`(statementSpy.executeQuery(anyString())).thenReturn(resultSetFake)
        }

        @Test
        fun `sends the correct query to get all recipes`() {
            sqlite.getAll()

            verify(statementSpy, times(1)).executeQuery("SELECT * FROM batches")
        }

        @Test
        fun `when there are recipe in the db, returns a list of Recipes`() {
            val result = sqlite.getAll()

            assertThat(result).usingRecursiveComparison().isEqualTo(listOf(batch1, batch2, batch3))
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
    inner class FindByName {
        @Test
        fun `sends the correct query to find batch by name`() {
            sqlite.findByName(name)

            verify(statementSpy, times(1)).executeQuery("SELECT * FROM batches WHERE name = $name")
        }

        @Test
        fun `when there is a corresponding batch in the db, returns the Batch`() {
            val result = sqlite.findByName(name)

            assertThat(result).usingRecursiveComparison().isEqualTo(batch)
        }
    }

    @Nested
    inner class FindByPourDate {
        @Test
        fun `sends the correct query to find batch by pour date`() {
            sqlite.findByPourDate(pourDate)

            verify(statementSpy, times(1)).executeQuery("SELECT * FROM batches WHERE pour_date = $pourDate")
        }

        @Test
        fun `when there is a corresponding batch in the db, returns the Batch`() {
            val result = sqlite.findByPourDate(pourDate)

            assertThat(result).usingRecursiveComparison().isEqualTo(batch)
        }
    }
}
