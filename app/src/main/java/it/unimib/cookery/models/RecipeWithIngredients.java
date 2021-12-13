package it.unimib.cookery.models;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class RecipeWithIngredients {
    @Embedded
    public Recipe Recipe;
    @Relation(
            parentColumn = "idRecipe",
            entityColumn = "idIngredient",
            associateBy = @Junction(RecipeIngredientCrossRef.class)
    )
    public List<Ingredient> ingredientList;
}
