package seedu.address.model;

import seedu.address.model.ingredient.Ingredient;

public interface IngredientModel {
    /**
     * Returns true if a ingredient with the same identity as {@code ingredient} exists in the restaurant book.
     */
    boolean hasIngredient(Ingredient ingredient);

    /**
     * Deletes the given ingredient.
     * The ingredient must exist in the restaurant book.
     */
    void deleteIngredient(Ingredient target);
}
