package use_cases

import entities.Recipe
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import ports.RecipeRepo

internal class CreateRecipeTest {
    val recipeRepoSpy = mock(RecipeRepo::class.java)

    @Test
    fun `invoking CreateRecipe calls the create method on the recipe repo with the recipe`() {
        val recipe = Recipe()

        CreateRecipe(recipe = recipe, recipeRepo = recipeRepoSpy).run()

        verify(recipeRepoSpy, times(1)).create(recipe)
    }
}