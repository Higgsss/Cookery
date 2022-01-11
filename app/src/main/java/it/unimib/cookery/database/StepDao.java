package it.unimib.cookery.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import it.unimib.cookery.models.IngredientPantry;
import it.unimib.cookery.models.Recipe;
import it.unimib.cookery.models.RecipeStep;


/**
 * Data Access Object (DAO) that provides methods that can be used to query,
 * update, insert, and delete data in the database.
 * https://developer.android.com/training/data-storage/room/accessing-data
 */
@Dao
public interface StepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStep(RecipeStep... step);

    @Query("SELECT * FROM RecipeStep WHERE pantry.idPantry LIKE :id")
    /*@Query("SELECT * FROM RecipeStep")
    List<RecipeStep> getAll();

    @Insert
    void insertRecipeList(List<RecipeStep> recipeStep);

    @Insert
    void insertAll(RecipeStep... recipeStep);

    @Insert
    void insertAlla(RecipeStep... recipeStep);

    @Delete
    void delete(RecipeStep recipe);

    @Query("DELETE FROM RecipeStep")
    void deleteAll();

    @Delete
    void deleteAllWithoutQuery(RecipeStep... recipeStep);*/
}
