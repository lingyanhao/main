package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.ingredient.Ingredient;

/**
 * The API that stores the ingredient side of the model.
 */
public interface IngredientModel {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Ingredient> PREDICATE_SHOW_ALL_INGREDIENTS = unused -> true;
    /**
     *
     * Returns true if a ingredient with the same identity as {@code ingredient} exists in the restaurant book.
     */
    boolean hasIngredient(Ingredient ingredient);

    /**
     * Deletes the given ingredient.
     * The ingredient must exist in the restaurant book.
     */
    void deleteIngredient(Ingredient target);

    /**
     * Adds the given ingredient.
     * {@code ingredient} must not already exist in the restaurant book.
     */
    void addIngredient(Ingredient ingredient);

    /**
     * Replaces the given member {@code target} with {@code editedMember}.
     * {@code target} must exist in the restaurant book.
     * The member identity of {@code editedMember}
     * must not be the same as another existing member in the restaurant book.
     */
    void setIngredient(Ingredient target, Ingredient editedItem);

    /** Returns an unmodifiable view of the filtered ingredient list */
    ObservableList<Ingredient> getFilteredIngredientList();

    /**
     * Updates the filter of the filtered ingredient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredIngredientList(Predicate<Ingredient> predicate);
}
