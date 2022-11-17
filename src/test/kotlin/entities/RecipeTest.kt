package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class RecipeTest {
    private val oliveOil = Ingredient(name = "Olive oil")
    private val coconutOil = Ingredient(name = "Coconut oil")

    @Test
    fun `given an id, it should be initialized to that id`() {
        val id = 42
        val recipe = Recipe(id = id)

        assertEquals(id, recipe.id)
    }

    @Test
    fun `given a name, it should be initialized to that name`() {
        val recipe = Recipe(name = "coffee")

        assertEquals("coffee", recipe.name)
    }

    @Test
    fun `should be able to change a recipe's name`() {
        val recipe = Recipe(name = "coffee")

        recipe.name = "lemon poppyseed"

        assertEquals("lemon poppyseed", recipe.name)
    }

    @Test
    fun `given a version, it should be initialized to that version`() {
        val recipe = Recipe(version = "v1.0.0")

        assertEquals("v1.0.0", recipe.version)
    }

    @Test
    fun `should be able to change the version`() {
        val recipe = Recipe(version = "v1.0.0")

        recipe.version = "v1.0.1"

        assertEquals("v1.0.1", recipe.version)
    }

    @Test
    fun `it should default its ingredients to an empty array`() {
        val recipe = Recipe()

        assertEquals(0, recipe.ingredients.size)
    }

    @Test
    fun `it should be able to add ingredients in its constructor`() {
        val recipe = Recipe(ingredients = arrayListOf(oliveOil, coconutOil))

        assertTrue(recipe.ingredients.contains(oliveOil))
        assertTrue(recipe.ingredients.contains(coconutOil))
    }

    @Test
    fun `it should be able to add ingredients`() {
        val recipe = Recipe()

        recipe.addIngredient(oliveOil)
        recipe.addIngredient(coconutOil)

        assertTrue(recipe.ingredients.contains(oliveOil))
        assertTrue(recipe.ingredients.contains(coconutOil))
    }

    @Test
    fun `it should be able to remove ingredients`() {
        val recipe = Recipe(ingredients = arrayListOf(oliveOil, coconutOil))

        recipe.removeIngredient(oliveOil)

        assertFalse(recipe.ingredients.contains(oliveOil))
        assertTrue(recipe.ingredients.contains(coconutOil))
    }

    @Test
    fun `when you try to remove an ingredient that is not in the recipe, it should not change the list of ingredients`() {
        val ingredients = arrayListOf(oliveOil)
        val recipe = Recipe(ingredients = ingredients)

        recipe.removeIngredient(coconutOil)

        assertEquals(ingredients, recipe.ingredients)
    }
}